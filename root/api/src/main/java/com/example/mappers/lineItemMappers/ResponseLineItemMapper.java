package com.example.mappers.lineItemMappers;


import com.example.DTOs.lineItemdto.ResponseLineItemDTO;
import com.example.mappers.orderMappers.ResponseOrderMapper;
import com.example.mappers.productMappers.ResponseProductMapper;
import com.example.domains.LineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ResponseProductMapper.class, ResponseOrderMapper.class})
public interface ResponseLineItemMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "orderId", source = "order.id")
    ResponseLineItemDTO toDTO(LineItem lineItem);

    List<LineItem> toDTOs(List<LineItem> lineItems);
}
