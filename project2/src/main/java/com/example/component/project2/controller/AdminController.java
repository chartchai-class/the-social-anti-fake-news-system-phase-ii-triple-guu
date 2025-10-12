package com.example.component.project2.controller;

import com.example.component.project2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/admin") @RequiredArgsConstructor
public class AdminController {
  private final UserService userService;
  private final NewsService newsService;
  private final VoteService voteService;
  private final com.example.component.project2.service.CommentService commentService;

  @PostMapping("/users/{id}/promote/member")
  public void promoteMember(@PathVariable Long id) { userService.promoteToMember(id); }

  @PostMapping("/users/{id}/promote/admin")
  public void promoteAdmin(@PathVariable Long id) { userService.promoteToAdmin(id); }

  @DeleteMapping("/news/{id}")
  public void removeNews(@PathVariable Long id) { newsService.softDelete(id); }

  @DeleteMapping("/votes/{id}")
  public void removeVote(@PathVariable Long id) { voteService.remove(id); }

  @DeleteMapping("/comments/{id}")
  public void removeComment(@PathVariable Long id) { commentService.remove(id); }
}

