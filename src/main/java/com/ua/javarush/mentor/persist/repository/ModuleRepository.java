package com.ua.javarush.mentor.persist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
