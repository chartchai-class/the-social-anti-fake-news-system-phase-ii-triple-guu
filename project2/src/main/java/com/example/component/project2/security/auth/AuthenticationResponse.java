package com.example.component.project2.security.auth;

import com.example.component.project2.dto.UserDto;
import lombok.Builder; import lombok.Data;

@Data @Builder
public class AuthenticationResponse {
  private String accessToken;
  private String refreshToken;
  private UserDto user;    // include user info in responses (handy for frontend)
}
