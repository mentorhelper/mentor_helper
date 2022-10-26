package com.ua.javarush.mentor.security.jwt;


import com.ua.javarush.mentor.dto.UserDTO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;


@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.clientSecret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenValiditySeconds}")
    private int jwtExpirationInMs;


    public String generateToken(UserDTO userPrincipal) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("user_id", userPrincipal.getId());
        tokenData.put("user_name", userPrincipal.getUsername());
        tokenData.put("role", userPrincipal.getRoleName());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setClaims(tokenData)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.getExpiration().before(new Date())) {
            throw new CredentialsExpiredException("JWT token expired ");
        }

        return parseLong(String.valueOf(claims.get("user_id")));
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error(ex.getMessage());
            throw new UnsupportedJwtException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error(ex.getMessage());
            throw new UnsupportedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new CredentialsExpiredException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error(ex.getMessage());
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            throw new UnsupportedJwtException("Invalid JWT token");
        }
    }
}
