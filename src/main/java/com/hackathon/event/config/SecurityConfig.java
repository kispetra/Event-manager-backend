package com.hackathon.event.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthProvider userAuthProvider;
    private final UserAuthEntryPoint userAuthEntryPoint;
    private static final String[] AUTH_WHITELIST = {
            "/login","/register", "/event/all", "/event/all/**", "/event/{eventId}/registrations",
            "/event", "/user/{userId}/event/all", "/user/{userId}/event", "/event/{eventId}", "/event/{eventId}/user-registrations",
            "/event/{eventId}/registrations/{registrationId}", "/event/{eventId}/invite", "/event/{eventId}/invited",
            "/event/{eventId}/participants/{participantId}", "/event/{eventId}/participants/{participantId}/week/{week_no}",
            "event/{eventId}/skills", "/event/{eventId}/participants/{participantId}/progress"

    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/event/{eventId}/registrations").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
        ;
        return http.build();
    }
}
