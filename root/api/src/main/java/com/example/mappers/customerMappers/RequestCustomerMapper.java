package com.example.mappers.customerMappers;


import com.example.DTOs.customerdto.RequestCustomerDTO;
import com.example.mappers.orderMappers.RequestOrderMapper;
import com.example.domains.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestOrderMapper.class)
public interface RequestCustomerMapper {
    @Mapping(source = "userId", target = "user.id")
    Customer toEntity(RequestCustomerDTO requestCustomerDTO);
}
