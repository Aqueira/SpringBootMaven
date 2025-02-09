package com.example.contracts;

import com.example.DTOs.authdto.RequestLoginDTO;
import com.example.DTOs.authdto.RequestRegistrationDTO;
import com.example.DTOs.authdto.ResponseLoginDTO;


public interface AuthService {
    void register(RequestRegistrationDTO registrationDTO);

    ResponseLoginDTO authenticate(RequestLoginDTO loginDTO);
}
