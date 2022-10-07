package com.ua.javarush.mentor.mapper;

import com.ua.javarush.mentor.annotations.DateConverter;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface Converter {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @DateConverter
    @Named("dateToString")
    default String beatifyDate(Date date) {
        return format.format(date);
    }
}
