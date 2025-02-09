package com.example.mappers.userMappers;

import com.example.DTOs.userdto.RequestUserDTO;
import com.example.mappers.customerMappers.RequestCustomerMapper;
import com.example.domains.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = RequestCustomerMapper.class)
public interface RequestUserMapper {
    User toEntity(RequestUserDTO requestUserDTO);
}
