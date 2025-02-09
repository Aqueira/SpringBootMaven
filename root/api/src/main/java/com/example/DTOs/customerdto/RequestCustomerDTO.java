package com.example.DTOs.customerdto;




import com.example.DTOs.orderdto.RequestOrderDTO;

import java.util.List;

public record RequestCustomerDTO(String name, String sector, List<RequestOrderDTO> orders, Long userId) {}
