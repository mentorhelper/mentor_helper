package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
