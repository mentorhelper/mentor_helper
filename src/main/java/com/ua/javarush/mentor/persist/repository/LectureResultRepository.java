package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.LectureResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureResultRepository extends JpaRepository<LectureResult, Long> {
}
