package com.online.platform.learning.service;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.models.User;
import com.online.platform.learning.models.UserCoursePanel;
import com.online.platform.learning.repository.CourseRepository;
import com.online.platform.learning.repository.UserCoursePanelRepository;
import com.online.platform.learning.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCoursePanelService {

    @Autowired
    private UserCoursePanelRepository userCoursePanelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void addToPanel(Long userId, Long courseId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalUser.isPresent() && optionalCourse.isPresent()) {
            User user = optionalUser.get();
            Course course = optionalCourse.get();

            UserCoursePanel panel = new UserCoursePanel();
            panel.setUser(user);
            panel.setCourse(course);
            course.setInPanel(true);
            courseRepository.save(course);
            userCoursePanelRepository.save(panel);
        }
    }

    @Transactional
    public void removeFromPanel(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setInPanel(false);
            courseRepository.save(course);
        }
        userCoursePanelRepository.deleteByCourseId(courseId);
    }

    public List<Course> getPanelCourses(Long userId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<UserCoursePanel> root = query.from(UserCoursePanel.class);

        query.select(root.get("course"))
                .where(builder.equal(root.get("user").get("id"), userId));

        return entityManager.createQuery(query).getResultList();
    }

    public boolean isCoursePanelByUser(Long userId, Long courseId) {
        if (userId != null) {
            return userCoursePanelRepository.existsByUserIdAndCourseId(userId, courseId);
        } else {
            return false;
        }
    }


}
