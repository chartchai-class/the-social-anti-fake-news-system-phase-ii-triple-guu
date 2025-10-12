package com.example.component.project2.service;

import com.example.component.project2.entity.*;

public interface VoteService {
  Vote upsert(News news, User user, boolean fake);
  long countFake(News news);
  long countNotFake(News news);
  void remove(Long voteId); // admin
}
