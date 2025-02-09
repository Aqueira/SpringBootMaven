package com.example.order.application.impl;

import com.example.DTOs.orderdto.RequestOrderDTO;
import com.example.DTOs.orderdto.ResponseOrderDTO;
import com.example.contracts.OrderService;
import com.example.contracts.Validator;
import com.example.domains.Order;
import com.example.exceptions.NotFoundException;
import com.example.order.data.OrderRepository;
import lombok.RequiredArgsConstructor;

import com.example.mappers.orderMappers.RequestOrderMapper;
import com.example.mappers.orderMappers.ResponseOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService, Validator<Long, Long> {
    private final OrderRepository orderRepository;
    private final RequestOrderMapper requestOrderMapper;
    private final ResponseOrderMapper responseOrderMapper;

    @Override
    @Transactional
    public ResponseOrderDTO create(RequestOrderDTO requestOrderDTO) {
       return responseOrderMapper.toDTO(orderRepository.save(requestOrderMapper.toEntity(requestOrderDTO)));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOrderDTO read(Long id) {
        return orderRepository.read(id)
                .map(responseOrderMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Order not found!"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseOrderDTO update(Long id, RequestOrderDTO requestOrderDTO) {
        Order order = orderRepository.read(id)
                .orElseThrow(() -> new NotFoundException("Order not found!"));
        order.setDeliverTo(requestOrderDTO.deliverTo());
        orderRepository.save(order);

        return responseOrderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseOrderDTO> findAll() {
        return responseOrderMapper.toDTOs(orderRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean validate(Long orderId, Long userId) {
        return orderRepository.read(orderId)
                .map(entity -> entity.getCustomer().getUser().getId().equals(userId)).orElse(false);
    }
}
