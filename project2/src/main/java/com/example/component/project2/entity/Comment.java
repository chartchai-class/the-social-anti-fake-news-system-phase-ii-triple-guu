package com.example.component.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Comment {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) private News news;
  @ManyToOne(fetch = FetchType.LAZY) private User user;

  @Column(columnDefinition="TEXT")
  private String text;

  private String imageUrl;           // optional comment image
  private Boolean removed;           // admin can hide
  private LocalDateTime createdAt;
}
