package com.leumas.finance.admin.service;

import com.leumas.finance.admin.controller.request.UserRequest;
import com.leumas.finance.admin.controller.response.UserResponse;
import com.leumas.finance.admin.entity.User;
import com.leumas.finance.admin.entity.enums.Role;
import com.leumas.finance.admin.mapper.UserMapper;
import com.leumas.finance.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        User newUser = UserMapper.toUser(userRequest);
        newUser.setRole(Role.USER); // por enquaqnto padroniza todo user como basico
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return UserMapper.toUserResponse(newUser);
    }

    public User createUserByOAuth(User user) {
        user.setRole(Role.USER); // por enquaqnto padroniza todo user como basico
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<UserDetails> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}