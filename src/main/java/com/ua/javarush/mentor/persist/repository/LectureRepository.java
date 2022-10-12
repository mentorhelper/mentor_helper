package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
