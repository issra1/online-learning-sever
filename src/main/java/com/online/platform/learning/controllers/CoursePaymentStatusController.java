package com.online.platform.learning.controllers;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.service.CoursePaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-payment-status")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CoursePaymentStatusController {

    @Autowired
    private CoursePaymentStatusService paymentStatusService;

    @PostMapping("/{courseId}/set-paid/{userId}")
    public ResponseEntity<?> setCourseAsPaidForUser(@PathVariable Long courseId, @PathVariable Long userId) {
        paymentStatusService.setCourseAsPaidForUser(courseId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/set-not-paid/{userId}")
    public ResponseEntity<?> setCourseAsNotPaidForUser(@PathVariable Long courseId, @PathVariable Long userId) {
        paymentStatusService.setCourseAsNotPaidForUser(courseId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paid-courses/{userId}")
    public ResponseEntity<List<Course>> getPaidCoursesByUser(@PathVariable Long userId) {
        List<Course> paidCourses = paymentStatusService.getPaidCoursesByUser(userId);
        return ResponseEntity.ok(paidCourses);
    }

    @GetMapping("/all-paied-courses/{userId}")
    public ResponseEntity<List<Course>> getAllPaiedCoursesForUser(@PathVariable Long userId) {
        List<Course> allPaiedCourses = paymentStatusService.getPaiedCoursesForUser(userId);
        return ResponseEntity.ok(allPaiedCourses);
    }

}
