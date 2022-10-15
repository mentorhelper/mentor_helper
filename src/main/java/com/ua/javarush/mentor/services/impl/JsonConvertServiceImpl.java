package com.ua.javarush.mentor.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.javarush.mentor.exceptions.Error;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.services.JsonConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;


@Service
@Slf4j
public class JsonConvertServiceImpl implements JsonConvertService {

    private final ObjectMapper objectMapper;

    public JsonConvertServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T convertJsonToObject(String json, Class<T> clazz) throws GeneralException {
        try {
            log.info("Converting json to object {}", clazz);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error while converting json to object", e);
            throw createGeneralException("Unable to parse json", HttpStatus.BAD_REQUEST, Error.UNABLE_TO_PARSE_JSON);
        }
    }

    @Override
    public String convertObjectToJson(Object object) throws GeneralException {
        try {
            log.info("Converting object to json {}", object);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error while converting object to json", e);
            throw createGeneralException("Unable to create json", HttpStatus.BAD_REQUEST, Error.UNABLE_TO_CREATE_JSON);
        }
    }

    @Override
    public <T> List<T> convertJsonToListObject(String json, Class<T> clazz) throws GeneralException {
        try {
            log.info("Converting json to list object {}", clazz);
            return objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            log.error("Error while converting json to list object", e);
            throw createGeneralException("Unable to create json", HttpStatus.BAD_REQUEST, Error.UNABLE_TO_PARSE_JSON);
        }
    }
}
