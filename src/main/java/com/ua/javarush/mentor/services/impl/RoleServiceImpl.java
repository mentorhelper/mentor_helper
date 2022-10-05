package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.RoleMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.repository.RoleRepository;
import com.ua.javarush.mentor.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public RoleDTO create(RoleCommand roleCommand) throws GeneralException {
        Role newRole = roleMapper.mapToEntity(roleCommand);
        Role roleFromDB = roleRepository.findByName(newRole.getName()).orElse(null);
        if (Objects.nonNull(roleFromDB)) {
            throw createGeneralException("Role with name " + newRole.getName() + " already exist", HttpStatus.BAD_REQUEST, Error.ROLE_ALREADY_EXISTS);
        } else {
            log.info("Create new role with name {}" + newRole.getName());
            return roleMapper.mapToDto(roleRepository.save(newRole));
        }
    }
}
