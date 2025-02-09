package com.example.mappers.lineItemMappers;


import com.example.DTOs.lineItemdto.RequestLineItemDTO;
import com.example.mappers.productMappers.RequestProductMapper;
import com.example.domains.LineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RequestProductMapper.class})
public interface RequestLineItemMapper {
    @Mapping(target = "product.id", source = "productId")
    LineItem toEntity(RequestLineItemDTO requestLineItemDTO);

    List<LineItem> toEntities(List<RequestLineItemDTO> requestLineItemDTOList);
}
