package com.example.DTOs.userdto;


import com.example.DTOs.customerdto.RequestCustomerDTO;
import com.example.enums.Role;

public record RequestUserDTO(String username, String password, Role role, RequestCustomerDTO customer) {
}
