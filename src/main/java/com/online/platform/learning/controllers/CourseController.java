package com.online.platform.learning.controllers;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/addCourse")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        if(course != null) {
            Course savedCourse = courseService.addCourse(course);
            return ResponseEntity.ok(savedCourse);
        }
        return null;
    }

    @GetMapping("/all/{roleName}")
    public ResponseEntity<List<Course>> getAllCourses(@PathVariable String roleName) {
        List<Course> courses = courseService.getAllCourses(roleName);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody Course updatedCourse) {
        Course updated = courseService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/accept-course/{id}")
    public ResponseEntity<String> acceptCourse(@PathVariable("id") Long idCourse) {
        courseService.accetpCourse(idCourse);
        return ResponseEntity.ok("Course status updated to Accepted");
    }


    @PutMapping("/delete-course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long idCourse) {
        courseService.deleteCourse(idCourse);
        return ResponseEntity.ok("Course status updated to Accepted");
    }

}