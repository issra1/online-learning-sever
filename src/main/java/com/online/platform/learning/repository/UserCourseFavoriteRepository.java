package com.online.platform.learning.repository;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.UserCourseFavorite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseFavoriteRepository extends JpaRepository<UserCourseFavorite, Long> {
    List<Course> findByUser_Id(Long userId);
    void deleteByCourseId (Long courseId);
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);


}
