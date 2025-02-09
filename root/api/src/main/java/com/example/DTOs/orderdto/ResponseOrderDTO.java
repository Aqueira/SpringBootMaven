package com.example.DTOs.orderdto;



import com.example.DTOs.lineItemdto.ResponseLineItemDTO;

import java.util.List;

public record ResponseOrderDTO(Long id, String deliverTo, Long customerId, List<ResponseLineItemDTO> lineItems) {
}
