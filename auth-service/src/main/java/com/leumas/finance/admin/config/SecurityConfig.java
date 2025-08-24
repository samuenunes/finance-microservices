package com.leumas.finance.admin.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        //.requestMatchers(HttpMethod.GET, "api/v1/api-docs/**").permitAll()
                        //.requestMatchers(HttpMethod.GET, "/swagger/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/v1/auth/login").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/**", "/api/v1/auth/oauth2/success").permitAll()

                        .anyRequest().denyAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        //.defaultSuccessUrl("/api/v1/auth/oauth2/success", true)
                        .successHandler(oAuth2SuccessHandler)
                )
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
