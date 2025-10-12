package com.example.component.project2.service;

import com.example.component.project2.entity.*;
import com.example.component.project2.repo.*;
import com.example.component.project2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepo;

  @Override public Page<Comment> list(News news, Pageable pageable) {
    return commentRepo.findByNewsAndRemovedFalseOrderByCreatedAtDesc(news, pageable);
  }

  @Override public Comment add(News news, User user, String text, String imageUrl) {
    Comment c = Comment.builder()
      .news(news).user(user).text(text).imageUrl(imageUrl)
      .removed(false).createdAt(LocalDateTime.now()).build();
    return commentRepo.save(c);
  }

  @Override public void remove(Long commentId) {
    Comment c = commentRepo.findById(commentId).orElseThrow();
    c.setRemoved(true);
    commentRepo.save(c);
  }
}

