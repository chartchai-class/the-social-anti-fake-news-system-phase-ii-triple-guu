package com.example.component.project2.service;

import org.springframework.data.domain.*;

import com.example.component.project2.entity.News;

public interface NewsService {
  Page<News> list(String q, Pageable pageable);
  News get(Long id);
  News create(News news);
  News update(Long id, News news);
  void softDelete(Long id); // admin “remove news” (hide from normal users)
}

