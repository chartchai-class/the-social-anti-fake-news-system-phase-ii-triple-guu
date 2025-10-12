package com.example.component.project2.service;

import com.example.component.project2.entity.*;
import com.example.component.project2.repo.VoteRepository;
import com.example.component.project2.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
  private final VoteRepository repo;

  @Override public Vote upsert(News news, User user, boolean fake) {
    Vote v = repo.findByNewsAndUser(news, user).orElse(
      Vote.builder().news(news).user(user).removed(false).build()
    );
    v.setFake(fake);
    v.setRemoved(false);
    return repo.save(v);
  }
  @Override public long countFake(News news) { return repo.countByNewsAndRemovedFalseAndFakeTrue(news); }
  @Override public long countNotFake(News news) { return repo.countByNewsAndRemovedFalseAndFakeFalse(news); }
  @Override public void remove(Long voteId) {
    Vote v = repo.findById(voteId).orElseThrow();
    v.setRemoved(true);
    repo.save(v);
  }
}
