package com.example.component.project2.repo;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.component.project2.entity.Comment;
import com.example.component.project2.entity.News;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Page<Comment> findByNewsAndRemovedFalseOrderByCreatedAtDesc(News news, Pageable pageable);
}

