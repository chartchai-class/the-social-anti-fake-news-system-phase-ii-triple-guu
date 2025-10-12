package com.example.component.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class News {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String topic;
  private String summary;
  @Column(columnDefinition="TEXT")
  private String content;

  private String reporterName;           
  private LocalDateTime reportedAt;

  private String imageUrl;              

   
}

