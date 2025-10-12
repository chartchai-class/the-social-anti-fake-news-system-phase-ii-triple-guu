package com.example.component.project2.service;

import com.example.component.project2.entity.*;
import com.example.component.project2.repo.UserRepository;
import com.example.component.project2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository repo;

  @Override public User getByUsername(String username) {
    return repo.findByUsername(username).orElseThrow();
  }
  @Override public List<User> listAll() { return repo.findAll(); }

  @Override public User promoteToMember(Long userId) {
    User u = repo.findById(userId).orElseThrow();
    if (!u.getRoles().contains(Role.ROLE_MEMBER)) u.getRoles().add(Role.ROLE_MEMBER);
    return repo.save(u);
  }

  @Override public User promoteToAdmin(Long userId) {
    User u = repo.findById(userId).orElseThrow();
    if (!u.getRoles().contains(Role.ROLE_ADMIN)) u.getRoles().add(Role.ROLE_ADMIN);
    return repo.save(u);
  }
}
