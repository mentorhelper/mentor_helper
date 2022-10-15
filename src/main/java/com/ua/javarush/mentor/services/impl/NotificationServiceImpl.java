package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.NotificationCommand;
import com.ua.javarush.mentor.command.SendEmailCommand;
import com.ua.javarush.mentor.enums.NotificationProvider;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.NotificationMapper;
import com.ua.javarush.mentor.persist.model.Notification;
import com.ua.javarush.mentor.persist.repository.NotificationRepository;
import com.ua.javarush.mentor.services.JsonConvertService;
import com.ua.javarush.mentor.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final JsonConvertService jsonConvertService;

    public NotificationServiceImpl(NotificationMapper notificationMapper, NotificationRepository notificationRepository, JsonConvertService jsonConvertService) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.jsonConvertService = jsonConvertService;
    }

    @Override
    public void saveNotification(NotificationCommand notificationCommand) {
        Notification notification = notificationMapper.toNotification(notificationCommand);
        notification.setDate(new Date());
        notificationRepository.save(notification);
        log.info("Notification saved: {}", notification);
    }

    @Override
    public NotificationCommand createNotification(SendEmailCommand sendEmailCommand, NotificationProvider provider) throws GeneralException {
        return NotificationCommand.builder()
                .provider(provider)
                .data(generateDataFromCommand(sendEmailCommand))
                .build();
    }

    private String generateDataFromCommand(SendEmailCommand sendEmailCommand) throws GeneralException {
        return jsonConvertService.convertObjectToJson(sendEmailCommand);
    }
}
