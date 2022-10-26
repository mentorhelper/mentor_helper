package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.exceptions.GeneralException;

import java.util.List;

public interface JsonConvertService {
    <T> T convertJsonToObject(String json, Class<T> clazz) throws GeneralException;
    String convertObjectToJson(Object object) throws GeneralException;
    <T> List<T> convertJsonToListObject(String json, Class<T> clazz) throws GeneralException;
}
