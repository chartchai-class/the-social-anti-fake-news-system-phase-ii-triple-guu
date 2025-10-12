package com.example.component.project2.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
  private Long id;
  private String username;
  private String firstname;
  private String lastname;
  private String email;
  private List<String> roles;
}

