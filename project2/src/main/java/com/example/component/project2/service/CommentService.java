package com.example.component.project2.service;

import org.springframework.data.domain.*;

import com.example.component.project2.entity.*;

public interface CommentService {
  Page<Comment> list(News news, Pageable pageable);
  Comment add(News news, User user, String text, String imageUrl);
  void remove(Long commentId); // admin hide
}

