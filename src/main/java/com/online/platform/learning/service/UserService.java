package com.online.platform.learning.service;

import com.online.platform.learning.models.ERole;
import com.online.platform.learning.models.Role;
import com.online.platform.learning.models.User;
import com.online.platform.learning.payload.request.SignupRequest;
import com.online.platform.learning.payload.response.MessageResponse;
import com.online.platform.learning.repository.RoleRepository;
import com.online.platform.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getUsersByRole(String roleName) {
        ERole erole = ERole.valueOf(roleName);
        Role role = roleRepository.findRoleByName(erole);
        return userRepository.findAllByRolesAndStatusNot(role, "Deleted");
    }

    public List<User> getUsersByRoleExceptionAdmin(String roleName) {
        ERole erole = ERole.valueOf(roleName);
        Role role = roleRepository.findRoleByName(erole);
        return userRepository.findAllByRolesNotAndStatusNot(role, "Deleted");
    }

    public ResponseEntity<String> updateUserStatus(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(((Role) user.getRoles().toArray()[0]).getName().name().equals("USER")) {
                ERole erole = ERole.valueOf("LEARNER");
                Role role = roleRepository.findRoleByName(erole);
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                user.setStatus("Accepté");
                user.setRoles(roles);
            } else {
                user.setStatus("Accepté");
            }
            userRepository.save(user);
            return ResponseEntity.ok("User status updated to Accepted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> cancelUserStatus(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("Refusé");
            userRepository.save(user);
            return ResponseEntity.ok("User status updated to Refused");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> updateUserProfile(@PathVariable String email,@RequestBody SignupRequest signUpRequest) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }
            // Create new user's account
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setAddress(signUpRequest.getAddress());
            user.setFullName(user.getFullName());
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setGender(signUpRequest.getGender());
            user.setInstitutionname(signUpRequest.getInstitutionname());
            user.setDepartment(signUpRequest.getDepartment());
            user.setExperience(signUpRequest.getExperience());
            user.setStatus(user.getStatus());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public List<User> fetchProfileByEmail(String email){
        return userRepository.findProfileByEmail(email);
    }

    public ResponseEntity<String> deleteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("Deleted");
            userRepository.save(user);
            return ResponseEntity.ok("User status updated to Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}


