package com.example.authentication.application.impl;

import com.example.DTOs.authdto.RequestLoginDTO;
import com.example.DTOs.authdto.RequestRegistrationDTO;
import com.example.DTOs.authdto.ResponseLoginDTO;
import com.example.authentication.application.JWTService;
import com.example.contracts.AuthService;
import com.example.domains.Customer;
import com.example.domains.User;
import com.example.enums.Role;
import com.example.exceptions.NotFoundException;
import com.example.user.data.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void register(RequestRegistrationDTO requestRegistrationDTO) {
        userRepository.save(User.builder()
                .username(requestRegistrationDTO.username())
                .password(bCryptPasswordEncoder.encode(requestRegistrationDTO.password()))
                .role(Role.USER)
                .customer(Customer.builder()
                        .name(requestRegistrationDTO.username())
                        .sector(requestRegistrationDTO.sector())
                        .build())
                .build());
    }

    @Override
    @Transactional
    public ResponseLoginDTO authenticate(RequestLoginDTO requestLoginDTO) {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(requestLoginDTO.username(), requestLoginDTO.password()));
        return userRepository.findByUsername(requestLoginDTO.username())
                .map(user -> new ResponseLoginDTO(jwtService.generateToken(user)))
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }
}
