package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.enums.Countries;
import com.ua.javarush.mentor.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
    @Override
    public List<String> getAllCountries() {
        return Countries.getAllCountries();
    }
}
