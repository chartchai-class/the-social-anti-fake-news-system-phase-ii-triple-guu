package com.example.component.project2.entity;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"news_id","user_id"}))
public class Vote {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) private News news;
  @ManyToOne(fetch = FetchType.LAZY) private User user;

  private Boolean fake;
  private Boolean removed;   
}

