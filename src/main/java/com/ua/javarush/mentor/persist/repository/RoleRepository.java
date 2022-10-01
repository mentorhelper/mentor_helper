package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
