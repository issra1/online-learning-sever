package com.online.platform.learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.platform.learning.models.ERole;
import com.online.platform.learning.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

  Role findRoleByName (ERole name);

}
