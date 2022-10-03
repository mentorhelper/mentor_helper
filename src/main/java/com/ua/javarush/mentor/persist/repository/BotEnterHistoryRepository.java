package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.BotEnterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotEnterHistoryRepository extends JpaRepository<BotEnterHistory, Long>{
}
