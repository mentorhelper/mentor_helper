package com.ua.javarush.mentor.persist.repository;


import com.ua.javarush.mentor.persist.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<com.ua.javarush.mentor.persist.model.Module, Long> {
    Optional<Module> findByName(String name);
}
