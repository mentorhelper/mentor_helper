package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.mapper.RoleMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.repository.RoleRepository;
import com.ua.javarush.mentor.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public RoleDTO create(RoleCommand roleCommand) {
        Role newRole = roleMapper.mapToEntity(roleCommand);
        roleRepository.save(newRole);
        log.info("Role '{}' was created", newRole.getName());
        return roleMapper.mapToDto(newRole);
    }
}
