package se331.lab.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime dateTime;
    private Long userId;
}
