package com.online.platform.learning.service;

import com.online.platform.learning.models.OptionQuestion;
import com.online.platform.learning.models.Question;
import com.online.platform.learning.models.Quiz;
import com.online.platform.learning.repository.QuestionRepository;
import com.online.platform.learning.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;


    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public Question createQuestion(Question question) {
        Quiz quiz = quizRepository.findById(Long.valueOf(12)).get();
        question.setQuiz(quiz);
        for(OptionQuestion optionQuestion : question.getOptions()) {
            optionQuestion.setQuestion(question);
        }
        return questionRepository.save(question);
    }
}
