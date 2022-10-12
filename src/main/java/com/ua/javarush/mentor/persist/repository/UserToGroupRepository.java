package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.UserToGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToGroupRepository extends JpaRepository<UserToGroup, Long> {
}
