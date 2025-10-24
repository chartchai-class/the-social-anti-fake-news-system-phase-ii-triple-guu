package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.News;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Long> {
	@Query("SELECT n FROM News n WHERE n.deleted = false")
	Page<News> findAllNotDeleted(Pageable pageable);

	@Query("SELECT n FROM News n WHERE n.id = :id AND n.deleted = false")
	News findByIdAndNotDeleted(@Param("id") Long id);

	// Search for non-deleted news
	@Query("SELECT n FROM News n WHERE n.deleted = false AND (LOWER(n.topic) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.reporterName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.shortDetail) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.fullDetail) LIKE LOWER(CONCAT('%', :query, '%')))")
	Page<News> searchNotDeleted(@Param("query") String query, Pageable pageable);

	// Search for all news (admin)
	@Query("SELECT n FROM News n WHERE (LOWER(n.topic) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.reporterName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.shortDetail) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.fullDetail) LIKE LOWER(CONCAT('%', :query, '%')))")
	Page<News> searchAll(@Param("query") String query, Pageable pageable);
}
