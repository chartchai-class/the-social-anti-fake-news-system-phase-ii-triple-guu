
package se331.lab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.lab.entity.UserEntity;
import se331.lab.repository.UserRepository;
import se331.lab.config.SupabaseStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.net.URI;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final SupabaseStorageProperties supabaseStorageProperties;

    @Autowired
    public UserController(UserRepository userRepository, SupabaseStorageProperties supabaseStorageProperties) {
        this.userRepository = userRepository;
        this.supabaseStorageProperties = supabaseStorageProperties;
    }

    // Endpoint to update user data
    // Endpoint to get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity updatedUser) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        // Update fields (add more as needed)
        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getSurname() != null) user.setSurname(updatedUser.getSurname());
        if (updatedUser.getName() != null) user.setName(updatedUser.getName());
        if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Admin can promote a READER to MEMBER
    @PostMapping("/{id}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> promoteToMember(@PathVariable Integer id) {
        UserEntity user = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (user.getRole() == se331.lab.entity.RoleType.MEMBER) {
            return ResponseEntity.badRequest().body("User is already a MEMBER");
        }
        if (user.getRole() == se331.lab.entity.RoleType.READER) {
            user.setRole(se331.lab.entity.RoleType.MEMBER);
            userRepository.save(user);
            return ResponseEntity.ok("User promoted to MEMBER");
        }
        return ResponseEntity.badRequest().body("User cannot be promoted");
    }

    // Endpoint to upload profile image
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            String extension = "";
            String originalName = file.getOriginalFilename();
            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalName.substring(dotIndex);
            }
            String filename = id + extension;

            // AWS S3 Client for Supabase S3 endpoint
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                supabaseStorageProperties.getAccessKey(),
                supabaseStorageProperties.getSecretKey()
            );
            S3Client s3 = S3Client.builder()
                .endpointOverride(URI.create(supabaseStorageProperties.getEndpoint()))
                .region(Region.of(supabaseStorageProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

            // Upload file
            PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(supabaseStorageProperties.getBucket())
                .key(filename)
                .contentType(file.getContentType())
                .build();
            s3.putObject(putOb, software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

            // Save the public URL to the user entity
            String publicUrl = supabaseStorageProperties.getEndpointOutput() + "/" + supabaseStorageProperties.getBucket() + "/" + filename;
            user.setProfileImage(publicUrl);
            userRepository.save(user);
            return ResponseEntity.ok("Profile image uploaded to Supabase S3");
        } catch (S3Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("S3 error: " + e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading image");
        }
    }

    // Endpoint to retrieve profile image
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<?> getProfileImage(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null || user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }
    String uploadDir = System.getProperty("user.dir") + "/profile-images/";
    java.io.File dir = new java.io.File(uploadDir);
    java.io.File[] files = dir.listFiles((d, name) -> name.startsWith(id + "."));
        if (files == null || files.length == 0) {
            return ResponseEntity.notFound().build();
        }
        java.io.File imageFile = files[0];
        try {
            org.springframework.core.io.Resource resource = new org.springframework.core.io.FileSystemResource(imageFile);
            return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving image");
        }
    }
}
