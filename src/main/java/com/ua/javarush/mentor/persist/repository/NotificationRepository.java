package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
