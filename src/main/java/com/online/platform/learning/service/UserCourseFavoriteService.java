package com.online.platform.learning.service;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.User;
import com.online.platform.learning.models.UserCourseFavorite;
import com.online.platform.learning.repository.CourseRepository;
import com.online.platform.learning.repository.UserCourseFavoriteRepository;
import com.online.platform.learning.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCourseFavoriteService {

    @Autowired
    private UserCourseFavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void addToFavorites(Long userId, Long courseId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalUser.isPresent() && optionalCourse.isPresent()) {
            User user = optionalUser.get();
            Course course = optionalCourse.get();

            UserCourseFavorite favorite = new UserCourseFavorite();
            favorite.setUser(user);
            favorite.setCourse(course);

            favoriteRepository.save(favorite);
        }
    }

    @Transactional
    public void removeFromFavorites(Long courseId) {
        favoriteRepository.deleteByCourseId(courseId);
    }

    public List<Course> getFavoriteCourses(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<UserCourseFavorite> root = query.from(UserCourseFavorite.class);

        Join<UserCourseFavorite, Course> courseJoin = root.join("course"); // Joining Course entity

        Predicate userPredicate = builder.equal(root.get("user").get("id"), userId);
        Predicate paiedPredicate = builder.isTrue(courseJoin.get("paiedOrNo")); // Assuming paiedOrNo is a field in the Course entity

        query.select(courseJoin)
                .where(builder.and(userPredicate, paiedPredicate));

        return entityManager.createQuery(query).getResultList();
    }

    public boolean isCourseFavoriteByUser(Long userId, Long courseId) {
        if(userId != null) {
            return favoriteRepository.existsByUserIdAndCourseId(userId, courseId);
        } else {
            return false;
        }
    }


}
