package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
