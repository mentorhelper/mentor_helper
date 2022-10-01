package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
