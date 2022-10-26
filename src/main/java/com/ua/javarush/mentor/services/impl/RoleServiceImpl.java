package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.RoleCommand;
import com.ua.javarush.mentor.command.RoleToPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.RoleDTO;
import com.ua.javarush.mentor.dto.RoleToPermissionDTO;
import com.ua.javarush.mentor.exceptions.UiError;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.RoleMapper;
import com.ua.javarush.mentor.mapper.RoleToPermissionMapper;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.RoleToPermission;
import com.ua.javarush.mentor.persist.repository.RoleRepository;
import com.ua.javarush.mentor.persist.repository.RoleToPermissionRepository;
import com.ua.javarush.mentor.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    public static final String NOT_FOUND_ROLE_ERROR = "Didn't found role";
    public static final String NOT_FOUND_PERMISSION_ERROR = "Didn't found permission";
    public static final String LOG_RESPONSE_ROLE = "Response role: {}";
    public static final String LOG_ROLE_WAS_CREATED = "Create new role with name {}";
    public static final String RESPONSE_ROLE_PERMISSION_FOR_ROLE_ID = "Response role permission for roleId {}";
    public static final String ADD_PERMISSION_TO_ROLE_ID = "Add permission {} to roleId {}";
    public static final String REMOVE_ROLE_ID_NAME = "Remove role: id={}, name={}";
    public static final String REMOVE_PERMISSION_FROM_ROLE_ID = "Remove permission {} from roleId {}";
    public static final String ROLE_WITH_ID_ALREADY_HAVE_PERMISSION = "Role with id {} already have permission: {}";

    private final RoleRepository roleRepository;
    private final RoleToPermissionRepository roleToPermissionRepository;
    private final RoleMapper roleMapper;
    private final RoleToPermissionMapper roleToPermissionMapper;

    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleToPermissionRepository roleToPermissionRepository,
                           RoleMapper roleMapper,
                           RoleToPermissionMapper roleToPermissionMapper) {
        this.roleRepository = roleRepository;
        this.roleToPermissionRepository = roleToPermissionRepository;
        this.roleMapper = roleMapper;
        this.roleToPermissionMapper = roleToPermissionMapper;
    }


    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public RoleDTO createRole(RoleCommand roleCommand) {
        Role newRole = roleMapper.mapToEntity(roleCommand);
        roleRepository.save(newRole);
        log.info(LOG_ROLE_WAS_CREATED + newRole.getName());
        return roleMapper.mapToDto(roleRepository.save(newRole));
    }

    @Override
    public RoleDTO getRoleById(Long id) throws GeneralException {
        Role role = fetchRole(id);
        log.info(LOG_RESPONSE_ROLE, role.getName());
        return roleMapper.mapToDto(role);
    }

    @Override
    public PageDTO<RoleDTO> getAllRoles(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<RoleDTO> users = roleRepository.findAll(paging)
                .map(roleMapper::mapToDto);
        return new PageDTO<>(users, paging);
    }

    @Override
    public RoleToPermissionDTO getRolePermissionById(Long roleId) throws GeneralException {
        Role role = fetchRole(roleId);
        log.info(RESPONSE_ROLE_PERMISSION_FOR_ROLE_ID, role.getId());
        return roleToPermissionMapper.mapToDto(role);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public RoleToPermissionDTO addPermissionToRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        if (!isExistPermissionByRole(roleId, roleToPermissionCommand)) {
            RoleToPermission newPermission = roleToPermissionMapper.mapToEntity(roleId, roleToPermissionCommand);
            roleToPermissionRepository.save(newPermission);
            log.info(ADD_PERMISSION_TO_ROLE_ID, newPermission.getPermission(), newPermission.getRoleId());
        } else {
            log.info(ROLE_WITH_ID_ALREADY_HAVE_PERMISSION, roleId, roleToPermissionCommand.getPermission());
        }
        return roleToPermissionMapper.mapToDto(fetchRole(roleId));
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public void removeRole(Long roleId) throws GeneralException {
        Role role = fetchRole(roleId);
        roleRepository.deleteById(roleId);
        role.getPermissions()
                .forEach(roleToPermission -> roleToPermissionRepository.deleteById(roleToPermission.getId()));
        log.info(REMOVE_ROLE_ID_NAME, role.getId(), role.getName());
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public void removePermissionInRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        RoleToPermission roleToPermission = fetchPermissionByRoleId(roleId, roleToPermissionCommand);
        log.info(REMOVE_PERMISSION_FROM_ROLE_ID, roleToPermissionCommand.getPermission(), roleId);
        roleToPermissionRepository.delete(roleToPermission);
    }

    @NotNull
    @Override
    public Role fetchRole(Long roleId) throws GeneralException {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_ROLE_ERROR, HttpStatus.NOT_FOUND, UiError.ROLE_NOT_FOUND));
    }

    @NotNull
    private RoleToPermission fetchPermissionByRoleId(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        return fetchRole(roleId).getPermissions().stream()
                .filter(permission -> permission.getPermission().equals(roleToPermissionCommand.getPermission()))
                .findAny()
                .orElseThrow(() -> createGeneralException(NOT_FOUND_PERMISSION_ERROR, HttpStatus.NOT_FOUND, UiError.ROLE_PERMISSION_NOT_FOUND));
    }

    private boolean isExistPermissionByRole(Long roleId, RoleToPermissionCommand roleToPermissionCommand) throws GeneralException {
        return fetchRole(roleId).getPermissions().stream()
                .anyMatch(permission -> permission.getPermission().equals(roleToPermissionCommand.getPermission()));
    }
}
