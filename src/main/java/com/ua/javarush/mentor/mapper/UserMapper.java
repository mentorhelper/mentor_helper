package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.services.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserMapper implements Converter {

    @Autowired
    protected RoleService roleService;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "registeredAt", source = "registeredAt", qualifiedByName = "dateToString")
    @Mapping(target = "telegramId", source = "telegramId")
    @Mapping(target = "telegramNickname", source = "telegramNickname")
    @Mapping(target = "salaryPerHour", source = "salaryPerHour")
    @Mapping(target = "salaryCurrency", source = "salaryCurrency")
    @Mapping(target = "roleName", expression = "java(user.getRoleId().getName())")
    public abstract UserDTO mapToDto(User user);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "registeredAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "telegramNickname", source = "telegramNickname")
    @Mapping(target = "salaryPerHour", source = "salaryPerHour")
    @Mapping(target = "salaryCurrency", source = "salaryCurrency")
    @Mapping(target = "roleId", expression = "java(roleService.fetchRole(userCommand.getRoleId()))")
    public abstract User mapToEntity(UserCommand userCommand) throws GeneralException;
}
