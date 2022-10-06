package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.LectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectionResultRepository extends JpaRepository<LectionResult, Long> {
}
