package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.command.ModuleCommand;
import com.ua.javarush.mentor.dto.ModuleDTO;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;

public interface ModuleService {

    ModuleDTO createModule(ModuleCommand moduleCommand);

    PageDTO<ModuleDTO> getAllModules(int page, int size, String sortBy);

    ModuleDTO getModuleById(Long id) throws GeneralException;

    ModuleDTO getModuleByName(String name) throws GeneralException;

    ModuleDTO updateModule(ModuleCommand moduleCommand, Long id) throws GeneralException;

    void removeModule(Long id) throws GeneralException;
}
