package com.example.component.project2.controller;

import com.example.component.project2.dto.VoteDto;
import com.example.component.project2.entity.*;
import com.example.component.project2.repo.NewsRepository;
import com.example.component.project2.service.UserService;
import com.example.component.project2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/votes") @RequiredArgsConstructor
public class VoteController {
  private final VoteService voteService;
  private final NewsRepository newsRepo;
  private final UserService userService;

  @PostMapping
  public VoteDto vote(@RequestBody VoteDto req, @RequestHeader("X-Username") String username) {
    News news = newsRepo.findById(req.getNewsId()).orElseThrow();
    User user = userService.getByUsername(username);
    var v = voteService.upsert(news, user, Boolean.TRUE.equals(req.getFake()));
    VoteDto out = new VoteDto();
    out.setNewsId(v.getNews().getId());
    out.setFake(v.getFake());
    return out;
  }
}

