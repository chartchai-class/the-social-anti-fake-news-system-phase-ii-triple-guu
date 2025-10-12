package com.example.component.project2.repo;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.example.component.project2.entity.News;

public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
  @Query("""
     select n from News n
     where (:q is null or lower(n.topic) like lower(concat('%',:q,'%'))
        or lower(n.summary) like lower(concat('%',:q,'%'))
        or lower(n.reporterName) like lower(concat('%',:q,'%')))
  """)
  Page<News> search(@Param("q") String q, Pageable pageable);
}

