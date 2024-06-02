package com.online.platform.learning.repository;

import com.online.platform.learning.models.Quiz;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @Transactional
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByIsAcceptedTrue();
}
