package com.online.platform.learning.controllers;

import com.online.platform.learning.models.Answer;
import com.online.platform.learning.models.Comment;
import com.online.platform.learning.models.Course;
import com.online.platform.learning.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.awt.SystemColor.text;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comments") // Change the base URL for the controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/all/{courseId}")
    public List<Comment> getAllCommentsByCourseId(@PathVariable Long courseId) {
        // Assuming commentRepository has a method findByCourseId that retrieves comments by courseId
        return commentService.getAllCommentsByCourseId(courseId);
    }




    @PostMapping("/add/{userId}/{courseId}")
    public Comment createComment(@PathVariable Long userId, @PathVariable Long courseId, @RequestBody Comment comment) {
        return commentService.createComment(userId, courseId, comment);
    }

    @DeleteMapping("/remove/{commentId}")
    public void removeComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
    }



}
