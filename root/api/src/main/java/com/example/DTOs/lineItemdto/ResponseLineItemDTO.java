package com.example.DTOs.lineItemdto;


public record ResponseLineItemDTO(Long id, Long orderId, Integer quantity, Long productId) {}
