package com.example.component.project2.service;

import java.util.List;

import com.example.component.project2.entity.*;

public interface UserService {
  User getByUsername(String username);
  List<User> listAll();
  User promoteToMember(Long userId);
  User promoteToAdmin(Long userId);
}

