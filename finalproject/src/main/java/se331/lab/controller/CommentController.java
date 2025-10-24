package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.lab.dto.CommentDto;
import se331.lab.entity.Comment;
import se331.lab.repository.CommentRepository;
import se331.lab.util.AppMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentRepository commentRepository;
    private final AppMapper mapper;

    @GetMapping("/news/{id}/comments")
    @PreAuthorize("hasAnyRole('READER','MEMBER','ADMIN')")
    public ResponseEntity<?> list(@PathVariable Long id) {
        List<CommentDto> dtos = mapper.commentToDto(commentRepository.findAllByNewsId(id));
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/news/{id}/comments")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<?> add(@PathVariable Long id, @RequestBody Comment comment) {
        comment.setDateTime(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return ResponseEntity.ok(mapper.commentToDto(saved));
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
