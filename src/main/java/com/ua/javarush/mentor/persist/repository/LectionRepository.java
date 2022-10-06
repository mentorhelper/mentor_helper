package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Lection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectionRepository extends JpaRepository<Lection, Long> {
}
