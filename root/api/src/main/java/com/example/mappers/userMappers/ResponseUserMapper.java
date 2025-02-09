package com.example.mappers.userMappers;

import com.example.DTOs.userdto.ResponseUserDTO;
import com.example.domains.User;
import com.example.mappers.customerMappers.ResponseCustomerMapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseCustomerMapper.class)
public interface ResponseUserMapper {
    ResponseUserDTO toDTO(User user);

    List<ResponseUserDTO> toDTOs(List<User> users);
}
