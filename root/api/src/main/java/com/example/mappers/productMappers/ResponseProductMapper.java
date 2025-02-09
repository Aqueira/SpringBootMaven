package com.example.mappers.productMappers;


import com.example.DTOs.productdto.ResponseProductDTO;
import com.example.domains.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseProductMapper {
    ResponseProductDTO toDTO(Product product);
    List<ResponseProductDTO> toDTOs(List<Product> products);
}
