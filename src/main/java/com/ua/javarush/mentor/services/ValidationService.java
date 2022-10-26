package com.ua.javarush.mentor.services;

public interface ValidationService {

    boolean isValidUsername(String username);

    boolean isValidPassword(String password);

    boolean isValidEmail(String email);

    boolean isValidCountry(String country);
}
