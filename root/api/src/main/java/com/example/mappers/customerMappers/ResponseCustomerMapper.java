package com.example.mappers.customerMappers;


import com.example.DTOs.customerdto.ResponseCustomerDTO;
import com.example.mappers.orderMappers.ResponseOrderMapper;
import com.example.domains.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseOrderMapper.class)
public interface ResponseCustomerMapper {
    @Mapping(target = "userId", source = "user.id")
    ResponseCustomerDTO toDTO(Customer customer);

    List<ResponseCustomerDTO> toDTOs(List<Customer> customers);
}
