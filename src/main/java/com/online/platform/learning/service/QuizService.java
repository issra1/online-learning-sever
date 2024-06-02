package com.online.platform.learning.service;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.OptionQuestion;
import com.online.platform.learning.models.Question;
import com.online.platform.learning.models.Quiz;
import com.online.platform.learning.repository.OptionRepository;
import com.online.platform.learning.repository.QuestionRepository;
import com.online.platform.learning.repository.QuizRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Quiz> getAllQuizzes(String roleName) {
        if ("ADMIN".equals(roleName)) {
            return quizRepository.findAll();
        } else {
           return quizRepository.findByIsAcceptedTrue();
        }
    }

    public Quiz createQuiz(Quiz quiz) {
        for(Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
            for(OptionQuestion optionQuestion : question.getOptions()) {
                optionQuestion.setQuestion(question);
            }
        }
        return quizRepository.save(quiz);
    }

    public ResponseEntity<String> deleteQuizById(@PathVariable Long id) {
        try {
            Quiz quiz = quizRepository.findById(id).get();
            for(Question question : quiz.getQuestions()) {
                for(OptionQuestion optionQuestion : question.getOptions()) {
                    optionRepository.deleteById(optionQuestion.getId());
                }
                quizRepository.deleteById(question.getId());
            }
            quizRepository.deleteById(id);
            return ResponseEntity.ok("Quiz deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting quiz");
        }
    }

    @Transactional
    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz existingQuiz = quizRepository.findById(id).orElse(null);

        if (existingQuiz != null) {
            existingQuiz.setTitle(updatedQuiz.getTitle());
            existingQuiz.setDescription(updatedQuiz.getDescription());

            // Remove existing questions and their options
            for (Question existingQuestion : existingQuiz.getQuestions()) {
                for (OptionQuestion option : existingQuestion.getOptions()) {
                    optionRepository.delete(option);
                }
                questionRepository.delete(existingQuestion);
            }
            existingQuiz.getQuestions().clear();

            // Flush to synchronize the database state
            entityManager.flush();

            // Add updated questions
            for (Question updatedQuestion : updatedQuiz.getQuestions()) {
                updatedQuestion.setQuiz(existingQuiz);

                // Manage options for the question
                List<OptionQuestion> optionQuestions = new ArrayList<>();
                for (OptionQuestion option : updatedQuestion.getOptions()) {
                    option.setQuestion(updatedQuestion);
                    optionQuestions.add(option);
                }
                updatedQuestion.setOptions(optionQuestions);

                // Merge or persist the updated question
                Question mergedQuestion = entityManager.merge(updatedQuestion);
                existingQuiz.getQuestions().add(mergedQuestion);
            }

            // Save and return the updated quiz
            return quizRepository.save(existingQuiz);
        } else {
            // Handle the case where the quiz with the given id does not exist
            return null;
        }
    }






    public Quiz getQuizById(Long courseId) {
        return quizRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    public List<OptionQuestion> getOptionsForQuestion(long questionId) {
        Question question = questionRepository.findById(questionId).get();
        return question.getOptions();
    }

    public ResponseEntity<String> deleteQuiz(Long idQuiz) {
        Optional<Quiz> quizOptional = quizRepository.findById(idQuiz);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setDeleted(true);
            quizRepository.save(quiz);
            return ResponseEntity.ok("Quiz status updated to Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> acceptQuiz(Long idQuiz) {
        Optional<Quiz> quizOptional = quizRepository.findById(idQuiz);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setAccepted(true);
            quizRepository.save(quiz);
            return ResponseEntity.ok("Quiz status updated to Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
