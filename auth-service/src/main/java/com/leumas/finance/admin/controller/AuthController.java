package com.leumas.finance.admin.controller;

import com.leumas.finance.admin.controller.request.LoginRequest;
import com.leumas.finance.admin.controller.request.UserRequest;
import com.leumas.finance.admin.config.TokenService;
import com.leumas.finance.admin.controller.response.LoginResponse;
import com.leumas.finance.admin.controller.response.UserResponse;
import com.leumas.finance.admin.entity.User;
import com.leumas.finance.admin.exception.UsernameOrPasswordInvalidException;
import com.leumas.finance.admin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try{
            UsernamePasswordAuthenticationToken userPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authentication = authenticationManager.authenticate(userPass);
            return ResponseEntity.ok(
                    new LoginResponse(tokenService.generateToken((User)authentication.getPrincipal()))
            );
        } catch (BadCredentialsException e) {
            throw new UsernameOrPasswordInvalidException("Usuário ou senha inválidos");
        }
    }
}
