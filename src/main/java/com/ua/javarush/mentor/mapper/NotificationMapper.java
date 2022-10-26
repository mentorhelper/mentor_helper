package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.command.NotificationCommand;
import com.ua.javarush.mentor.persist.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "data", source = "data")
    @Mapping(target = "notificationProvider", source = "provider")
    Notification toNotification(NotificationCommand notificationCommand);
}
