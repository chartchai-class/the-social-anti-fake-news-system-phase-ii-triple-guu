package com.example.component.project2.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsDto {
  private Long id;
  private String topic;
  private String summary;
  private String content;
  private String reporterName;
  private LocalDateTime reportedAt;
  private String imageUrl;
  private Long fakeCount;
  private Long notFakeCount;
  private Boolean isFake; // derived: fakeCount > notFakeCount
}

