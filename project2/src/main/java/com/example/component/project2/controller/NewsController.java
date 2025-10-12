package com.example.component.project2.controller;

import com.example.component.project2.dto.NewsDto;
import com.example.component.project2.entity.News;
import com.example.component.project2.service.NewsService;
import com.example.component.project2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/news") @RequiredArgsConstructor
public class NewsController {
  private final NewsService newsService;
  private final VoteService voteService;

  @GetMapping
  public Page<NewsDto> list(@RequestParam(required=false) String q,
                            @RequestParam(defaultValue="0") int page,
                            @RequestParam(defaultValue="10") int size) {
    var p = newsService.list(q, PageRequest.of(page, size, Sort.by("reportedAt").descending()));
    return p.map(this::toDto);
  }

  @GetMapping("/{id}")
  public NewsDto get(@PathVariable Long id) {
    return toDto(newsService.get(id));
  }

  @PostMapping        // ROLE_MEMBER or ROLE_ADMIN
  public NewsDto create(@RequestBody News n) {
    n.setReportedAt(java.time.LocalDateTime.now());
    return toDto(newsService.create(n));
  }

  @PutMapping("/{id}") // ROLE_MEMBER or ROLE_ADMIN (owner or admin)
  public NewsDto update(@PathVariable Long id, @RequestBody News n) {
    return toDto(newsService.update(id, n));
  }

  private NewsDto toDto(News n) {
    var dto = new NewsDto();
    dto.setId(n.getId());
    dto.setTopic(n.getTopic());
    dto.setSummary(n.getSummary());
    dto.setContent(n.getContent());
    dto.setReporterName(n.getReporterName());
    dto.setReportedAt(n.getReportedAt());
    dto.setImageUrl(n.getImageUrl());
    long f = voteService.countFake(n);
    long nf = voteService.countNotFake(n);
    dto.setFakeCount(f);
    dto.setNotFakeCount(nf);
    dto.setIsFake(f > nf);
    return dto;
  }
}

