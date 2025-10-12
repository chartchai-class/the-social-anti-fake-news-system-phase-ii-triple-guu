package com.example.component.project2.service;

import com.example.component.project2.entity.*;
import com.example.component.project2.repo.UserRepository;
import com.example.component.project2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    List<GrantedAuthority> authorities = u.getRoles().stream()
      .map(r -> new SimpleGrantedAuthority(r.name()))
      .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
      u.getUsername(),
      u.getPassword(),
      u.isEnabled(),
      true,
      true,
      true,
      authorities
    );
  }
}
