package com.example.configurations.filters.mapper;


import com.example.DTOs.userdto.ResponseUserDTO;
import com.example.domains.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilterMapper {
    User responseDTOToEntity(ResponseUserDTO userDTO);
}
