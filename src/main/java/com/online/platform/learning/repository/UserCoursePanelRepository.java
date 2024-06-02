package com.online.platform.learning.repository;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.UserCoursePanel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCoursePanelRepository extends JpaRepository<UserCoursePanel, Long> {
    List<Course> findByUser_Id(Long userId);
    void deleteByCourseId (Long courseId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
