package com.online.platform.learning.controllers;
import com.online.platform.learning.models.User;
import com.online.platform.learning.payload.request.SignupRequest;
import com.online.platform.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/roles/{roleName}")
    public List<User> getUsersByRoleName(@PathVariable String roleName) {
        return userService.getUsersByRole(roleName);
    }

    @GetMapping("/userroles/{roleName}")
    public List<User> getUsersByRoleExceptionAdmin(@PathVariable String roleName) {
        return userService.getUsersByRoleExceptionAdmin(roleName);
    }



    @PutMapping("updatestatus/{email}")
    public ResponseEntity<String> updateUserStatus(@PathVariable String email) {
        userService.updateUserStatus(email);
        return ResponseEntity.ok("User status updated to Accepted");
    }

    @PutMapping("cancelstatus/{email}")
    public ResponseEntity<String> cancelUserStatus(@PathVariable String email) {
        userService.cancelUserStatus(email);
        return ResponseEntity.ok("User status updated to Accepted");
    }

    @PutMapping("/updateuserprofile/{email}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String email, @RequestBody SignupRequest signupRequest) {
        ResponseEntity<?> userobj = userService.updateUserProfile(email,signupRequest);
        return userobj;
    }

    @GetMapping("/getprofildetails/{email}")
    public ResponseEntity<List<User>> getProfileDetails(@PathVariable String email)  {
        List<User> users = userService.fetchProfileByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("delete-user/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User status updated to Accepted");
    }



}
