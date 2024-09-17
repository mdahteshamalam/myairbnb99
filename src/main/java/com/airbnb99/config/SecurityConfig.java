package com.airbnb99.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
       // http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();

//        http.authorizeHttpRequests()
//                .requestMatchers("/api/v1/users/addUser","/api/v1/users/login")
//                .permitAll()
//                .requestMatchers("/api/v1/verify/addJWT").hasRole("ADMIN")
//                .requestMatchers("/api/v1/users/profile").hasAnyRole("ADMIN","USER")
//                .anyRequest().authenticated();
        return http.build();
    }
}
