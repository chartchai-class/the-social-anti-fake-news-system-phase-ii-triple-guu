package com.example.component.project2.controller;

import com.example.component.project2.dto.CommentDto;
import com.example.component.project2.entity.*;
import com.example.component.project2.repo.NewsRepository;
import com.example.component.project2.service.CommentService;
import com.example.component.project2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/comments") @RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;
  private final NewsRepository newsRepo;
  private final UserService userService;

  @GetMapping
  public Page<CommentDto> list(@RequestParam Long newsId,
                               @RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="10") int size) {
    News news = newsRepo.findById(newsId).orElseThrow();
    return commentService.list(news, PageRequest.of(page, size)).map(this::toDto);
  }

  @PostMapping
  public CommentDto add(@RequestParam Long newsId, @RequestParam String text,
                        @RequestParam(required=false) String imageUrl,
                        @RequestHeader("X-Username") String username) {
    News news = newsRepo.findById(newsId).orElseThrow();
    User user = userService.getByUsername(username);
    return toDto(commentService.add(news, user, text, imageUrl));
  }

  private CommentDto toDto(Comment c) {
    CommentDto d = new CommentDto();
    d.setId(c.getId());
    d.setNewsId(c.getNews().getId());
    d.setUsername(c.getUser().getUsername());
    d.setText(c.getText());
    d.setImageUrl(c.getImageUrl());
    d.setCreatedAt(c.getCreatedAt());
    return d;
  }
}

