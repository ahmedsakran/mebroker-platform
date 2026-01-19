package com.mebroker.authservice.repository;

import com.mebroker.authservice.entity.UserSystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSystemRoleRepository extends JpaRepository<UserSystemRole, Long> {
}
