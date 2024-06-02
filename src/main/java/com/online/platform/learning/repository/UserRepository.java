package com.online.platform.learning.repository;

import java.util.List;
import java.util.Optional;

import com.online.platform.learning.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.platform.learning.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
  List<User> findProfileByEmail(String email);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
  List<User> findAllByRolesAndStatusNot(Role role, String status);
  List<User> findAllByRolesNotAndStatusNot(Role role, String status);


}
