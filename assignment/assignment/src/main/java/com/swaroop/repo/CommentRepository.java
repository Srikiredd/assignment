package com.swaroop.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.swaroop.model.Comment;
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
