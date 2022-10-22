package com.ua.javarush.mentor.services;

import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.DeviceType;
import com.ua.javarush.mentor.exceptions.GeneralException;

import java.time.Instant;
import java.util.UUID;


public interface AuthorizationService {
    UserDTO login(String username, String password, DeviceType deviceType) throws GeneralException;

    UserDTO refreshTokens(DeviceType deviceType, String refreshToken) throws GeneralException;

    UUID getRefreshToken(Long userId, Instant expiredDate);
}
