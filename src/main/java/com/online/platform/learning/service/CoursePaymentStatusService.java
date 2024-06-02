package com.online.platform.learning.service;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.CoursePaymentStatus;
import com.online.platform.learning.models.User;
import com.online.platform.learning.repository.CoursePaymentStatusRepository;
import com.online.platform.learning.repository.CourseRepository;
import com.online.platform.learning.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursePaymentStatusService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CoursePaymentStatusRepository paymentStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void setCourseAsPaidForUser(Long courseId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalUser.isPresent() && optionalCourse.isPresent()) {
            User user = optionalUser.get();
            Course course = optionalCourse.get();
            CoursePaymentStatus coursePaymentStatus = new CoursePaymentStatus();
            coursePaymentStatus.setUser(user);
            coursePaymentStatus.setCourse(course);
            course.setPaiedOrNo(true);
            courseRepository.save(course);
            paymentStatusRepository.save(coursePaymentStatus);
        }
    }

    public void setCourseAsNotPaidForUser(Long courseId, Long userId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setPaiedOrNo(false);
            courseRepository.save(course);
        }
        paymentStatusRepository.deleteByCourseId(courseId);
    }


    public List<Course> getPaidCoursesByUser(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<CoursePaymentStatus> root = query.from(CoursePaymentStatus.class);

        query.select(root.get("course"))
                .where(builder.equal(root.get("user").get("id"), userId),
                        builder.isTrue(root.get("paid")));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Course> getPaiedCoursesForUser(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<CoursePaymentStatus> root = query.from(CoursePaymentStatus.class);

        query.select(root.get("course"))
                .where(builder.equal(root.get("user").get("id"), userId));

        return entityManager.createQuery(query).getResultList();
    }

    public boolean isCoursePaiedByUser(Long userId, Long courseId) {
        if(userId != null) {
            return paymentStatusRepository.existsByUserIdAndCourseId(userId, courseId);
        } else {
            return false;
        }
    }

}
