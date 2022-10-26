package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.enums.Countries;
import com.ua.javarush.mentor.services.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationServiceImpl implements ValidationService {

    private static final String PATTERN_FOR_USERNAME = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PATTERN_FOR_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final String PATTERN_FOR_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public boolean isValidUsername(String username) {
        return username.matches(PATTERN_FOR_USERNAME);
    }

    @Override
    public boolean isValidPassword(String password) {
        return password.matches(PATTERN_FOR_PASSWORD);
    }

    @Override
    public boolean isValidEmail(String email) {
        return email.matches(PATTERN_FOR_EMAIL);
    }

    @Override
    public boolean isValidCountry(String country) {
        return Countries.contains(country);
    }
}

