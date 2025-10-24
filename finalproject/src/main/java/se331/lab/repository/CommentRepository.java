package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	java.util.List<Comment> findAllByNewsId(Long newsId);
}
