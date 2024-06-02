package com.online.platform.learning.repository;

import com.online.platform.learning.models.OptionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionQuestion, Long> {
    List<OptionQuestion> findByQuestionId(Long questionId);
}
