package com.example.DTOs.customerdto;



import com.example.DTOs.orderdto.ResponseOrderDTO;

import java.util.List;

public record ResponseCustomerDTO(Long id, String name, String sector, List<ResponseOrderDTO> orders, Long userId, Long version) {}
