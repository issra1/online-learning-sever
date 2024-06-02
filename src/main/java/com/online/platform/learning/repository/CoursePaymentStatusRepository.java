package com.online.platform.learning.repository;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.CoursePaymentStatus;
import com.online.platform.learning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePaymentStatusRepository extends JpaRepository<CoursePaymentStatus, Long> {
    void deleteByCourseAndUser(Course course, User user);

    void deleteByCourseId (Long courseId);

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);


}
