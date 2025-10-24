    // (Removed misplaced getCommentCount outside the class)
package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Transient
    public int getCommentCount() {
        if (comments == null) return 0;
        return comments.size();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String shortDetail;
    @Column(length = 5000)
    private String fullDetail;

    @Enumerated(EnumType.STRING)
    private NewsStatus status;

    private String reporterName;
    private LocalDateTime dateTime;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "submitted_by_id")
    private UserEntity submittedBy;

    @OneToMany(mappedBy = "news")
    private List<Comment> comments;

    @OneToMany(mappedBy = "news")
    private List<Vote> votes;
    
    // Soft delete flag
    @Builder.Default
    private boolean deleted = false;
    
    @Transient
    public int getFakeCount() {
        if (votes == null) return 0;
        return (int) votes.stream().filter(v -> v.getVoteType() == VoteType.FAKE).count();
    }
    
    @Transient
    public int getNotFakeCount() {
        if (votes == null) return 0;
        return (int) votes.stream().filter(v -> v.getVoteType() == VoteType.NOT_FAKE).count();
    }
}
