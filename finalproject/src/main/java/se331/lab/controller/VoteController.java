package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.lab.entity.News;
import se331.lab.entity.UserEntity;
import se331.lab.entity.Vote;
import se331.lab.entity.VoteType;
import se331.lab.repository.NewsRepository;
import se331.lab.repository.UserRepository;
import se331.lab.repository.VoteRepository;
import se331.lab.service.NewsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class VoteController {
    private static final Logger log = LoggerFactory.getLogger(VoteController.class);
    private final VoteRepository voteRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsService newsService;

    @PostMapping("/news/{id}/vote")
    @PreAuthorize("hasAnyRole('READER','MEMBER','ADMIN')")
    public ResponseEntity<?> vote(@PathVariable Long id, @RequestParam Long userId, @RequestParam VoteType type) {
        log.info("Received vote: newsId={}, userId={}, type={}", id, userId, type);
        News news = newsRepository.findById(id).orElse(null);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (news == null || user == null) return ResponseEntity.badRequest().build();
        // remove existing vote by this user
        voteRepository.findByUserIdAndNewsId(userId, id).ifPresent(v -> voteRepository.delete(v));
        Vote vote = Vote.builder().news(news).user(user).voteType(type).build();
    voteRepository.save(vote);
    // Reload news to ensure votes list is up-to-date
    News updatedNews = newsRepository.findById(id).orElse(news);
    newsService.recalculateNewsStatus(updatedNews.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/votes/{voteId}")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<?> deleteVote(@PathVariable Long voteId) {
        Vote vote = voteRepository.findById(voteId).orElse(null);
        if (vote == null) return ResponseEntity.notFound().build();
        Long newsId = vote.getNews().getId();
        voteRepository.delete(vote);
        newsService.recalculateNewsStatus(newsId);
        return ResponseEntity.noContent().build();
    }
}
