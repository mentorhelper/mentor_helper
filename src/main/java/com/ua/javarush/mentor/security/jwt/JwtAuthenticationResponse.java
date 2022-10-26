package com.ua.javarush.mentor.security.jwt;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthenticationResponse {
    @NonNull
    private String accessToken;

    private String tokenType = "Bearer";
}
