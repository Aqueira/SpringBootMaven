package com.example.mappers.productMappers;


import com.example.DTOs.productdto.RequestProductDTO;
import com.example.domains.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestProductMapper {
    Product toEntity(RequestProductDTO requestProductDTO);
}
