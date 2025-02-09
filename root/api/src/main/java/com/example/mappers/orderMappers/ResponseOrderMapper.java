package com.example.mappers.orderMappers;

import com.example.DTOs.orderdto.ResponseOrderDTO;
import com.example.mappers.lineItemMappers.ResponseLineItemMapper;
import com.example.domains.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ResponseLineItemMapper.class)
public interface ResponseOrderMapper {
    @Mapping(target = "customerId", source = "customer.id")
    ResponseOrderDTO toDTO(Order order);

    List<ResponseOrderDTO> toDTOs(List<Order> orders);
}
