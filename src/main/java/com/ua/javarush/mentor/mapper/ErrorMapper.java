package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.dto.ErrorDTO;
import com.ua.javarush.mentor.exceptions.UiError;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ErrorMapper {

    @Mapping(target = "code", source = "code")
    @Mapping(target = "message", source = "message")
    ErrorDTO mapToDto(UiError uiError);

    @Mapping(target = "code", source = "code")
    @Mapping(target = "message", source = "message")
    List<ErrorDTO> mapToDto(List<UiError> uiErrors);
}
