package com.online.platform.learning.repository;

import com.online.platform.learning.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.isDeleted = false")
    List<Course> findAllIsNotDeleted();

    @Query("SELECT c FROM Course c WHERE c.isAccepted = :isAccepted AND c.isDeleted = false")
    List<Course> findIsAcceptedAndIsNotDeleted(@Param("isAccepted") boolean isAccepted);
}
