package com.leumas.finance.admin.controller;

import com.leumas.finance.admin.config.TokenService;
import com.leumas.finance.admin.controller.request.UserRequest;
import com.leumas.finance.admin.controller.response.LoginResponse;
import com.leumas.finance.admin.entity.User;
import com.leumas.finance.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

    @GetMapping("/success")
    public ResponseEntity<String> oauth2Success() {
        System.out.println("CAINDO AQUI NO CONTROLLER ++++++++++");
        return ResponseEntity.ok("Logado BY GOOGLE");
    }
}
