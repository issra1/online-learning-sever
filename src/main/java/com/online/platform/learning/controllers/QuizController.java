package com.online.platform.learning.controllers;

import com.online.platform.learning.models.OptionQuestion;
import com.online.platform.learning.models.Quiz;
import com.online.platform.learning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @GetMapping("/all/{roleName}")
    public List<Quiz> getAllQuizzes(@PathVariable String roleName) {
        return quizService.getAllQuizzes(roleName);
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuizById(@PathVariable Long id) {
        try {
            quizService.deleteQuizById(id);
            return ResponseEntity.ok("Quiz deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting quiz");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        Quiz savedQuiz = quizService.updateQuiz(id, updatedQuiz);
        return ResponseEntity.ok(savedQuiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/{questionId}/options")
    public ResponseEntity<?> getOptionsForQuestion(@PathVariable long questionId) {
        try {
            // Call the service layer to fetch options for the given questionId
            List<OptionQuestion> options = quizService.getOptionsForQuestion(questionId);
            return ResponseEntity.ok(options);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching options");
        }
    }

    @PutMapping("/accept-quiz/{id}")
    public ResponseEntity<String> acceptQuiz(@PathVariable("id") Long idQuiz) {
        quizService.acceptQuiz(idQuiz);
        return ResponseEntity.ok("Course status updated to Accepted");
    }


    @PutMapping("/delete-course/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("id") Long idQuiz) {
        quizService.deleteQuiz(idQuiz);
        return ResponseEntity.ok("Course status updated to Accepted");
    }




}




