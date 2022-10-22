package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    Config findByName(String name);
}
