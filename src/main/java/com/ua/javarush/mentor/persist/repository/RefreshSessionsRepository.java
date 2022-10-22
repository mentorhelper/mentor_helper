package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.RefreshSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface RefreshSessionsRepository extends JpaRepository<RefreshSessions, Long> {
    RefreshSessions findByRefreshToken(UUID refreshToken);

    RefreshSessions findByUserIdAndExpiredDate(Long id, Instant expiredDate);

    void deleteByUserId(Long userId);
}
