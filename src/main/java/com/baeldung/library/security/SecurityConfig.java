package com.baeldung.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration
 * 
 * @author speter555
 * @since 0.1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String AUTHORS_PATH = "/authors/**";
    private static final String ADMIN_ROLE = "ADMIN";

    /**
     * {@link SecurityFilterChain} producer, "/authors/**" secured, use only admin role with basic authentication !
     * 
     * @param http
     *            {@link HttpSecurity} element
     * @return builded {@link SecurityFilterChain}
     * @throws Exception
     *             if any error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers(HttpMethod.POST, AUTHORS_PATH)
                        .hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, AUTHORS_PATH)
                        .hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, AUTHORS_PATH)
                        .hasAnyRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, AUTHORS_PATH)
                        .hasAnyRole(ADMIN_ROLE)
                        .anyRequest()
                        .authenticated())
                .httpBasic();
        return http.build();
    }
}
