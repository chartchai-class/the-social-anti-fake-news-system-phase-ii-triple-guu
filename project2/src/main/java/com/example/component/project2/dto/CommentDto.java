package com.example.component.project2.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDto {
  private Long id;
  private Long newsId;
  private String username;
  private String text;
  private String imageUrl;
  private LocalDateTime createdAt;
}

