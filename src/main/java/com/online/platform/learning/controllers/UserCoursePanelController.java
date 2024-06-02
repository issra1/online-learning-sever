package com.online.platform.learning.controllers;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.service.UserCourseFavoriteService;
import com.online.platform.learning.service.UserCoursePanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/panel")
public class UserCoursePanelController {

    @Autowired
    private UserCoursePanelService panelService;

    @PostMapping("/users/{userId}/add/{courseId}")
    public void addToPanel(@PathVariable Long userId, @PathVariable Long courseId) {
        panelService.addToPanel(userId, courseId);
    }

    @DeleteMapping("/users/remove/{courseId}")
    public void removeFromFavorites(@PathVariable Long courseId) {
        panelService.removeFromPanel(courseId);
    }

    @GetMapping("/users/{userId}")
    public List<Course> getPanelCourses(@PathVariable Long userId) {
        return panelService.getPanelCourses(userId);
    }

    @GetMapping("/users/{userId}/courses/{courseId}/isPanel")
    public boolean isCourseFavoriteByUser(@PathVariable Long userId, @PathVariable Long courseId) {
        return panelService.isCoursePanelByUser(userId, courseId);
    }

}
