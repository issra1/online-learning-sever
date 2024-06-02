package com.online.platform.learning.repository;

import com.online.platform.learning.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCourseId(Long courseId);
}
