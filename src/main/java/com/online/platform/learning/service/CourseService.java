package com.online.platform.learning.service;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;


    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses(String roleName) {
        if ("ADMIN".equals(roleName)) {
            return courseRepository.findAllIsNotDeleted();
        } else {
            return courseRepository.findIsAcceptedAndIsNotDeleted(true);
        }
    }


    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        // Update the fields of the existing course with the new values
        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setEnrolledDate(updatedCourse.getEnrolledDate());
        existingCourse.setInstructor(updatedCourse.getInstructor());
        existingCourse.setInstructorInstitution(updatedCourse.getInstructorInstitution());
        existingCourse.setEnrolledCount(updatedCourse.getEnrolledCount());
        existingCourse.setYoutubeUrl(updatedCourse.getYoutubeUrl());
        existingCourse.setWebsiteUrl(updatedCourse.getWebsiteUrl());
        existingCourse.setCourseType(updatedCourse.getCourseType());
        existingCourse.setPrice(updatedCourse.getPrice());
        existingCourse.setSkillLevel(updatedCourse.getSkillLevel());
        existingCourse.setLanguage(updatedCourse.getLanguage());
        existingCourse.setDescription(updatedCourse.getDescription());

        // Save the updated course
        return courseRepository.save(existingCourse);
    }


    public ResponseEntity<String> accetpCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setAccepted(true);
            courseRepository.save(course);
            return ResponseEntity.ok("course status updated to Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public ResponseEntity<String> deleteCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setDeleted(true);
            courseRepository.save(course);
            return ResponseEntity.ok("course status updated to Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
