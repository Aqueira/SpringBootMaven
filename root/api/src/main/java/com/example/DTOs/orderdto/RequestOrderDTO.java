package com.example.DTOs.orderdto;


import com.example.DTOs.lineItemdto.RequestLineItemDTO;

import java.util.List;

public record RequestOrderDTO(String deliverTo, Long customerId, List<RequestLineItemDTO> lineItems) {
}
