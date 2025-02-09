package com.example.contracts;


import com.example.DTOs.customerdto.CurrentDTO;
import com.example.DTOs.customerdto.RequestCustomerDTO;
import com.example.DTOs.customerdto.ResponseCustomerDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface CustomerService {
    ResponseCustomerDTO read(Long id);

    ResponseCustomerDTO update(Long id, RequestCustomerDTO entity);

    List<ResponseCustomerDTO> findAll();

    CurrentDTO getCurrent(UserDetails userDetails);
}

