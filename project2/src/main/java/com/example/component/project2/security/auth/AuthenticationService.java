package com.example.component.project2.security.auth;

import com.example.component.project2.config.JwtService;
import com.example.component.project2.dto.UserDto;
import com.example.component.project2.entity.*;
import com.example.component.project2.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository users;
  private final PasswordEncoder encoder;
  private final JwtService jwt;
  private final AuthenticationManager authManager;

  public AuthenticationResponse register(RegisterRequest r) {
    User u = User.builder()
      .username(r.getUsername())
      .email(r.getEmail())
      .password(encoder.encode(r.getPassword()))
      .firstname(r.getFirstname())
      .lastname(r.getLastname())
      .enabled(true)
      .roles(List.of(Role.ROLE_READER))
      .build();

    u = users.save(u);
    String access = jwt.generateToken(u);
    String refresh = jwt.generateRefreshToken(u);

    UserDto dto = toDto(u);
    return AuthenticationResponse.builder().accessToken(access).refreshToken(refresh).user(dto).build();
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
    dto.setRoles(u.getRoles().stream().map(Role::name)                 // or: .map(r -> r.name())
     .collect(Collectors.toList()) );
    return dto;
  }
}
