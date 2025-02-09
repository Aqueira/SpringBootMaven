package com.example.mappers.orderMappers;



import com.example.DTOs.orderdto.RequestOrderDTO;
import com.example.mappers.lineItemMappers.RequestLineItemMapper;
import com.example.domains.Customer;
import com.example.domains.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RequestLineItemMapper.class})
public abstract class RequestOrderMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "toReference")
    public abstract Order toEntity(RequestOrderDTO requestOrderDTO);

    public abstract List<Order> toEntities(List<RequestOrderDTO> requestOrderDTOs);

    @Named("toReference")
    protected Customer toReference(Long id) {
        return entityManager.getReference(Customer.class, id);
    }
}
