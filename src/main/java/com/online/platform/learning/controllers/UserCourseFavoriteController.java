package com.online.platform.learning.controllers;

import com.online.platform.learning.models.Course;
import com.online.platform.learning.service.UserCourseFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/favorites")
public class UserCourseFavoriteController {

    @Autowired
    private UserCourseFavoriteService favoriteService;

    @PostMapping("/users/{userId}/add/{courseId}")
    public void addToFavorites(@PathVariable Long userId, @PathVariable Long courseId) {
        favoriteService.addToFavorites(userId, courseId);
    }

    @DeleteMapping("/users/remove/{courseId}")
    public void removeFromFavorites(@PathVariable Long courseId) {
        favoriteService.removeFromFavorites(courseId);
    }

    @GetMapping("/users/{userId}")
    public List<Course> getFavoriteCourses(@PathVariable Long userId) {
        return favoriteService.getFavoriteCourses(userId);
    }

    @GetMapping("/users/{userId}/courses/{courseId}/isFavorite")
    public boolean isCourseFavoriteByUser(@PathVariable Long userId, @PathVariable Long courseId) {
        return favoriteService.isCourseFavoriteByUser(userId, courseId);
    }

}
