package com.online.platform.learning.service;

import com.online.platform.learning.models.Comment;
import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.User;
import com.online.platform.learning.repository.CommentRepository;
import com.online.platform.learning.repository.CourseRepository;
import com.online.platform.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CourseRepository courseRepository; // Assuming you have a CourseRepository

    @Autowired
    private UserRepository userRepository; // Assuming you have a CourseRepository


    public List<Comment> getAllCommentsByCourseId(Long courseId) {
        return commentRepository.findByCourseId(courseId);
    }



    public Comment createComment(Long userId, Long courseId,Comment comment) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (user.isPresent() && course.isPresent()) {
            User user1 = user.get();
            Course course1 = course.get();
            comment.setUser(user1);
            comment.setCourse(course1);
            comment.setCommentText(comment.getCommentText());
            comment.setTimestamp(LocalDateTime.now());
            return commentRepository.save(comment);
        } else return null;
    }

    public void removeComment(Long commentId) {
        // Delete the comment
        commentRepository.deleteById(commentId);
    }


}
