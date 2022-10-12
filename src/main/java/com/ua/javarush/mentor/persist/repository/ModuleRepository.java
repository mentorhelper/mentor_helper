package com.ua.javarush.mentor.persist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<com.ua.javarush.mentor.persist.model.Module, Long> {
}
