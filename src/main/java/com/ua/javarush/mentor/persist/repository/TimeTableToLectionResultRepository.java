package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.TimeTableToLectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableToLectionResultRepository extends JpaRepository<TimeTableToLectionResult, Long> {
}
