package com.ua.javarush.mentor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.web.cors.allow.origins}")
    private String[] allowedOrigins;

    @Value("${app.web.cors.allow.methods}")
    private String[] allowedMethods;

    @Value("${app.web.cors.allow.headers}")
    private String[] allowedHeaders;

    @Value("${app.web.cors.allow.max.age}")
    private Long maxAge;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders)
                .allowCredentials(true)
                .maxAge(maxAge);
    }
}
