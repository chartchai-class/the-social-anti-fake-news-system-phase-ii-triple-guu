package com.example.component.project2.service;

import com.example.component.project2.entity.News;
import com.example.component.project2.repo.NewsRepository;
import com.example.component.project2.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
  private final NewsRepository repo;

  @Override public Page<News> list(String q, Pageable pageable) {
    return repo.search(q == null || q.isBlank() ? null : q, pageable);
  }
  @Override public News get(Long id) { return repo.findById(id).orElseThrow(); }
  @Override public News create(News n) { return repo.save(n); }
  @Override public News update(Long id, News n) {
    News cur = get(id);
    cur.setTopic(n.getTopic());
    cur.setSummary(n.getSummary());
    cur.setContent(n.getContent());
    cur.setImageUrl(n.getImageUrl());
    return repo.save(cur);
  }
  @Override public void softDelete(Long id) {
    
    repo.deleteById(id);
    
  }
}
