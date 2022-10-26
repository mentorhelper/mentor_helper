package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.enums.DeviceType;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.exceptions.InvalidRefreshSessionException;
import com.ua.javarush.mentor.exceptions.SessionExpiredException;
import com.ua.javarush.mentor.exceptions.UiError;
import com.ua.javarush.mentor.mapper.UserMapper;
import com.ua.javarush.mentor.persist.model.RefreshSessions;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.RefreshSessionsRepository;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.security.jwt.JwtTokenProvider;
import com.ua.javarush.mentor.services.AuthorizationService;
import com.ua.javarush.mentor.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static com.google.common.hash.Hashing.sha256;
import static com.ua.javarush.mentor.exceptions.GeneralExceptionUtils.createGeneralException;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.Instant.now;
import static java.util.Objects.isNull;
import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final int COUNT_SESSIONS = 3;
    private static final int TIME_SESSION_SECONDS = 10 * 24 * 60 * 60 ;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshSessionsRepository refreshSessionsRepository;
    private final JwtTokenProvider jwtTokenGenerator;

    private final UserMapper userMapper;

    public AuthorizationServiceImpl(UserService userService, UserRepository userRepository, RefreshSessionsRepository refreshSessionsRepository, JwtTokenProvider jwtTokenGenerator, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.refreshSessionsRepository = refreshSessionsRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDTO login(String email, String password, DeviceType deviceType) throws GeneralException {
        User user = userService.findUserByEmail(email.toLowerCase());
        userService.matchPassword(user, password);
        if (Boolean.FALSE.equals(user.isEmailVerified())) {
            log.warn("Registration was not confirmed by the user {}", user);
            throw createGeneralException("User not verified by email", HttpStatus.BAD_REQUEST, UiError.APPLICATION_ERROR);
        }
        log.info("User {} successfully logged in", user.getEmail());
        checkCountSessions(user);
        return generateRefreshSession(user, deviceType);
    }

    @Override
    public UserDTO refreshTokens(DeviceType deviceType, String refreshToken) throws GeneralException {
        RefreshSessions refreshSession = getRefreshSession(fromString(refreshToken));
        User user = refreshSession.getUser();
        String fingerPrint = generateFingerPrint(refreshToken, user.getId());

        removeUserRefreshSessions(user, refreshSession);
        validateRefreshToken(refreshSession, fingerPrint);

        return generateRefreshSession(user, deviceType);
    }

    @Override
    public UUID getRefreshToken(Long userId, Instant expiredDate) {
        RefreshSessions refreshSession = refreshSessionsRepository.findByUserIdAndExpiredDate(userId, expiredDate);
        return refreshSession.getRefreshToken();
    }

    private RefreshSessions getRefreshSession(UUID refreshToken) throws GeneralException {
        RefreshSessions refreshSession = refreshSessionsRepository.findByRefreshToken(refreshToken);
        if (isNull(refreshSession)) {
            log.warn("Could not find refresh session by refresh token {}", refreshToken);
            throw createGeneralException("Could not find refresh session by refresh token " + refreshToken.toString(), HttpStatus.BAD_REQUEST, UiError.APPLICATION_ERROR);
        }
        return refreshSession;
    }

    private void validateRefreshToken(RefreshSessions refreshSession, String fingerPrint) {
        if (refreshSession.getExpiredDate().isBefore(now())) {
            log.warn("Refresh token {} has expired", refreshSession.getRefreshToken());
            throw new SessionExpiredException(refreshSession.getRefreshToken().toString());
        }
        if (!fingerPrint.equals(refreshSession.getFingerPrint())) {
            log.warn("Invalid refresh session by finger print '{}'", fingerPrint);
            throw new InvalidRefreshSessionException(fingerPrint);
        }
    }

    private void removeUserRefreshSessions(User user, RefreshSessions refreshSession) {
        user.getRefreshSessions().removeIf(session -> session.getId().equals(refreshSession.getId()));
        refreshSessionsRepository.deleteById(refreshSession.getId());
    }

    private UserDTO generateRefreshSession(User user, DeviceType deviceType) {
        Instant expiredDate = now().plusSeconds(TIME_SESSION_SECONDS);
        UUID refreshToken = generateRefreshToken();
        String fingerPrint = generateFingerPrint(valueOf(refreshToken), user.getId());
        RefreshSessions refreshSessions = generateRefreshSession(user, refreshToken, fingerPrint, expiredDate, deviceType);
        user.getRefreshSessions().add(refreshSessions);
        userRepository.save(user);
        return mapUserToUserDto(user, expiredDate);
    }

    private String generateFingerPrint(String refreshToken, Long userId) {
        return sha256().hashString(refreshToken + userId, UTF_8).toString();
    }

    private RefreshSessions generateRefreshSession(User user, UUID refreshToken, String fingerPrint, Instant expiredDate, DeviceType deviceType) {
        return new RefreshSessions(user, refreshToken, deviceType, fingerPrint, expiredDate, now());
    }

    private UUID generateRefreshToken() {
        return randomUUID();
    }

    private UserDTO mapUserToUserDto(User user, Instant expiredDate) {
        UserDTO userDTO = userMapper.mapToDto(user);
        String token = jwtTokenGenerator.generateToken(userDTO);
        userDTO.setAccessToken(token);
        userDTO.setSessionExpiredDate(expiredDate);
        return userDTO;
    }

    private void checkCountSessions(User user) {
        Set<RefreshSessions> refreshSessions = user.getRefreshSessions();
        if (isCountSessionsValid(refreshSessions.size())) {
            user.getRefreshSessions().removeAll(refreshSessions);
            refreshSessionsRepository.deleteByUserId(user.getId());
        }
    }

    private boolean isCountSessionsValid(int currentCountSessions) {
        return currentCountSessions >= COUNT_SESSIONS;
    }

}
