package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
