package com.example.component.project2.security.auth;

import lombok.Data;
@Data
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
  private String firstname;
  private String lastname;
}
