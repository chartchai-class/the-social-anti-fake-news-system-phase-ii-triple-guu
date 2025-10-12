package com.example.component.project2.repo;

import org.springframework.data.jpa.repository.*;

import com.example.component.project2.entity.*;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
  long countByNewsAndRemovedFalseAndFakeTrue(News news);
  long countByNewsAndRemovedFalseAndFakeFalse(News news);
  Optional<Vote> findByNewsAndUser(News news, User user);
}

