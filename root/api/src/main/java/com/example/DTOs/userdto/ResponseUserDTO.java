package com.example.DTOs.userdto;


import com.example.DTOs.customerdto.ResponseCustomerDTO;
import com.example.enums.Role;

public record ResponseUserDTO(Long id, String username, String password, Role role, ResponseCustomerDTO customer) {
}
