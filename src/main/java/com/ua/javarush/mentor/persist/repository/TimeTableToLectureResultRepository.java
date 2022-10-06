package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.TimeTableToLectureResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableToLectureResultRepository extends JpaRepository<TimeTableToLectureResult, Long> {
}
