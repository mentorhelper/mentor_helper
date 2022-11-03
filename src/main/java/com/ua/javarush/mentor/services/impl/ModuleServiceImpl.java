package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.ModuleCommand;
import com.ua.javarush.mentor.dto.ModuleDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.mapper.ModuleMapper;
import com.ua.javarush.mentor.persist.model.Module;
import com.ua.javarush.mentor.persist.repository.ModuleRepository;
import com.ua.javarush.mentor.services.ModuleService;
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
public class ModuleServiceImpl implements ModuleService {
    public static final String NOT_FOUND_BY_ID_MODULE_ERROR = "Module not found, id: ";
    public static final String NOT_FOUND_BY_NAME_MODULE_ERROR = "Module not found, name: ";
    public static final String LOG_RESPONSE_MODULE = "Response module - name: {}, number: {}";
    public static final String LOG_MODULE_WAS_CREATED = "Module created - id: {}, name: {}";
    public static final String LOG_MODULE_WAS_UPDATED = "Module updated - id: {}, name: {}";
    public static final String LOG_MODULE_WAS_REMOVED = "Module removed - id: {}, name: {}";

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    public ModuleServiceImpl(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public ModuleDTO createModule(ModuleCommand moduleCommand) {
        Module newModule = moduleMapper.mapToEntity(moduleCommand);
        moduleRepository.save(newModule);
        log.info(LOG_MODULE_WAS_CREATED, newModule.getId(), newModule.getName());
        return moduleMapper.mapToDto(moduleRepository.save(newModule));
    }

    @Override
    public PageDTO<ModuleDTO> getAllModules(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ModuleDTO> modules = moduleRepository.findAll(paging)
                .map(moduleMapper::mapToDto);
        return new PageDTO<>(modules, paging);
    }

    @Override
    public ModuleDTO getModuleById(Long id) throws GeneralException {
        Module module = fetchModule(id);
        log.info(LOG_RESPONSE_MODULE, module.getName(), module.getModuleNumber());
        return moduleMapper.mapToDto(module);
    }

    @Override
    public ModuleDTO getModuleByName(String name) throws GeneralException {
        Module module = fetchModule(name);
        log.info(LOG_RESPONSE_MODULE, module.getName(), module.getModuleNumber());
        return moduleMapper.mapToDto(module);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public ModuleDTO updateModule(ModuleCommand moduleCommand, Long id) throws GeneralException {
        Module moduleDb = fetchModule(id);
        moduleMapper.updateModuleFromDto(moduleCommand, moduleDb);
        moduleRepository.save(moduleDb);
        log.info(LOG_MODULE_WAS_UPDATED, moduleDb.getId(), moduleDb.getName());
        return moduleMapper.mapToDto(moduleDb);
    }

    @Override
    @Transactional(rollbackFor = GeneralException.class)
    public void removeModule(Long id) throws GeneralException {
        Module module = fetchModule(id);
        moduleRepository.deleteById(id);
        log.info(LOG_MODULE_WAS_REMOVED, id, module.getName());
    }

    @NotNull
    private Module fetchModule(Long id) throws GeneralException {
        return moduleRepository.findById(id)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_BY_ID_MODULE_ERROR + id, HttpStatus.NOT_FOUND, Error.MODULE_NOT_FOUND));
    }

    @NotNull
    private Module fetchModule(String name) throws GeneralException {
        return moduleRepository.findByName(name)
                .orElseThrow(() -> createGeneralException(NOT_FOUND_BY_NAME_MODULE_ERROR + name, HttpStatus.NOT_FOUND, Error.MODULE_NOT_FOUND));
    }
}
