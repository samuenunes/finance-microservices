package com.leumas.finance.admin.config;

import com.leumas.finance.admin.controller.response.LoginResponse;
import com.leumas.finance.admin.entity.User;
import com.leumas.finance.admin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String token =  userService.findUserByEmail(email)
                .map(userDetails -> tokenService.generateToken((User) userDetails))
                .orElseGet(() -> {
                    return tokenService.generateToken(
                            userService.createUserByOAuth(
                                    User.builder()
                                            .name(username)
                                            .email(email)
                                            .password(email)//por enquanto
                                            .build()
                            )
                    );
                });


        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
