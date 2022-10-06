package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.controller.user.UserRequest;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.RoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, uses = {})
public abstract class UserMapper {
    @Autowired
    protected RoleRepository roleRepository;

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "registeredAt", source = "registeredAt")
    @Mapping(target = "telegramId", source = "telegramId")
    @Mapping(target = "telegramNickname", source = "telegramNickname")
    @Mapping(target = "salaryPerHour", source = "salaryPerHour")
    @Mapping(target = "salaryCurrency", source = "salaryCurrency")
    @Mapping(target = "secretPhrase", source = "secretPhrase")
    @Mapping(target = "roleName", expression = "java(user.getRoleId().getName())")
    public abstract UserDTO mapToDto(User user);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "registeredAt", expression = "java(new java.sql.Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "telegramNickname", source = "telegramNickname")
    @Mapping(target = "salaryPerHour", source = "salaryPerHour")
    @Mapping(target = "salaryCurrency", source = "salaryCurrency")
    @Mapping(target = "secretPhrase", source = "secretPhrase")
    @Mapping(target = "roleId", expression = "java(roleRepository.findById(userRequest.getRoleId()).get())")
    public abstract User mapToEntity(UserRequest userRequest);
}
