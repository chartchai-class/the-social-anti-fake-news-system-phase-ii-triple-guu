package com.example.component.project2.security.auth;

import com.example.component.project2.config.JwtService;
import com.example.component.project2.dto.UserDto;
import com.example.component.project2.entity.*;
import com.example.component.project2.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository users;
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwt;
  private final AuthenticationManager authManager;

  public AuthenticationResponse register(RegisterRequest request) {
    

    List<Role> assignedRoles = new ArrayList<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                assignedRoles.add(Role.valueOf(roleName)); 
            }
        } else {
            assignedRoles.add(Role.ROLE_READER);
        }
    User user = User.builder()
            .username(request.getUsername())
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(assignedRoles)
            .build();

    var savedUser = repository.save(user);
    var jwtToken = jwt.generateToken(savedUser);
    var refreshToken = jwt.generateRefreshToken(savedUser);

    //saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .user(toDto(savedUser))
            .build();
}
    

  public AuthenticationResponse authenticate(AuthenticationRequest r) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword()));
    User u = users.findByUsername(r.getUsername()).orElseThrow();
    String access = jwt.generateToken(u);
    String refresh = jwt.generateRefreshToken(u);
    return AuthenticationResponse.builder()
      .accessToken(access)
      .refreshToken(refresh)
      .user(toDto(u))
      .build();
  }

  private UserDto toDto(User u) {
    UserDto dto = new UserDto();
    dto.setId(u.getId());
    dto.setUsername(u.getUsername());
    dto.setFirstname(u.getFirstname());
    dto.setLastname(u.getLastname());
    dto.setEmail(u.getEmail());
    dto.setRoles(u.getRoles().stream().map(Role::name)                 
     .collect(Collectors.toList()) );
    return dto;
  }
}
