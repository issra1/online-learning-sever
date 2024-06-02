package com.online.platform.learning.repository;

import com.online.platform.learning.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Define custom query methods if needed
}
