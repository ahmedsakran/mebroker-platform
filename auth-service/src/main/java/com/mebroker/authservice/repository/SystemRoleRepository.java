package com.mebroker.authservice.repository;

import com.mebroker.authservice.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {

    Optional<SystemRole> findByTitle(String title);
    Optional<SystemRole> findByRoleTitle(String roleTitle);
    Optional<SystemRole> findByRoleNumber(Integer roleNumber);
}
