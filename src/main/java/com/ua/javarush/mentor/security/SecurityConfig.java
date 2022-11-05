package com.ua.javarush.mentor.security;

import com.ua.javarush.mentor.filter.JwtAuthenticationFilter;
import com.ua.javarush.mentor.security.jwt.JwtAuthenticationEntryPoint;
import com.ua.javarush.mentor.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    public static final String WEB_LOGOUT = "/logout";
    private static final String API_AUTH_ENDPOINTS = "/api/auth/**";
    private static final String API_REGISTER_ENDPOINTS = "/api/user/create";
    public static final String API_EMAIL_ENDPOINTS = "/api/user/email/confirm/**/**";
    public static final String API_RESET_PASSWORD_ENDPOINTS = "/api/user/password/reset/**";
    public static final String WEB_SIGN_UP_ENDPOINTS = "/sign-up";
    public static final String WEB_EMPTY = "/";
    public static final String WEB_LOGIN = "/login";
    public static final String WEB_HOME = "/home";

    private static final String[] OPEN_API_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**"};
    public static final String ERROR_TRUE = "?error=true";

    private final UserServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(UserServiceImpl userDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage(WEB_LOGIN)
                    .failureUrl(WEB_LOGIN + ERROR_TRUE)
                    .defaultSuccessUrl(WEB_HOME)
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(WEB_LOGOUT))
                    .logoutSuccessUrl(WEB_EMPTY)
                .and()
                    .rememberMe()
                .and()
                    .authorizeRequests()
                    .antMatchers("/dashboard/**", "/actuator/**").permitAll()
                    .antMatchers(WEB_EMPTY, "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                    .antMatchers(API_AUTH_ENDPOINTS, API_REGISTER_ENDPOINTS, API_EMAIL_ENDPOINTS, API_RESET_PASSWORD_ENDPOINTS).permitAll()
                    .antMatchers(WEB_SIGN_UP_ENDPOINTS, WEB_LOGIN).permitAll()
                    .antMatchers(OPEN_API_ENDPOINTS).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied")
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
