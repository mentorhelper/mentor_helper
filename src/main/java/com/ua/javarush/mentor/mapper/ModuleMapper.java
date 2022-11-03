package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.command.ModuleCommand;
import com.ua.javarush.mentor.dto.ModuleDTO;
import com.ua.javarush.mentor.persist.model.Module;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {
    ModuleDTO mapToDto(Module module);

    Module mapToEntity(ModuleCommand moduleCommand);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateModuleFromDto(ModuleCommand moduleCommand, @MappingTarget Module module);

}
