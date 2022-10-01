package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.RoleToPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleToPermissionRepository extends JpaRepository<RoleToPermission, Integer> {
}
