package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
