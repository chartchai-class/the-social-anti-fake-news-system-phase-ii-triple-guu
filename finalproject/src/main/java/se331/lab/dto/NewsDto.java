package se331.lab.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewsDto {
    private Long id;
    private String topic;
    private String shortDetail;
    private String fullDetail;
    private String status;
    private String reporterName;
    private LocalDateTime dateTime;
    private String imageUrl;
    private Long submittedById;
    private List<CommentDto> comments;
    private Integer fakeCount;
    private Integer notFakeCount;
    private Integer commentCount;
}
