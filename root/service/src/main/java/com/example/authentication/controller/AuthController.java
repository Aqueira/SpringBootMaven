package com.example.authentication.controller;


import com.example.DTOs.authdto.RequestLoginDTO;
import com.example.DTOs.authdto.RequestRegistrationDTO;
import com.example.DTOs.authdto.ResponseLoginDTO;
import com.example.authentication.application.impl.AuthServiceImpl;
import com.example.contracts.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<ResponseLoginDTO> authenticate(@RequestBody RequestLoginDTO requestLoginDTO) {
        return ResponseEntity.ok(authService.authenticate(requestLoginDTO));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody RequestRegistrationDTO requestRegistrationDTO) {
        authService.register(requestRegistrationDTO);
        return ResponseEntity.ok().build();
    }
}
