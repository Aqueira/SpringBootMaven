package com.example.customer.application.impl;


import com.example.DTOs.customerdto.CurrentDTO;
import com.example.DTOs.customerdto.RequestCustomerDTO;
import com.example.DTOs.customerdto.ResponseCustomerDTO;
import com.example.configurations.rabbitmq.RabbitMQService;
import com.example.contracts.CustomerService;
import com.example.customer.data.CustomerRepository;
import com.example.domains.Customer;
import com.example.exceptions.NotFoundException;
import com.example.mappers.customerMappers.ResponseCustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ResponseCustomerMapper responseCustomerMapper;
    private final RabbitMQService rabbitMQService;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "customerCache", key = "#id")
    public ResponseCustomerDTO read(Long id) {
        return customerRepository.read(id)
                .map(responseCustomerMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Customer not found!"));
    }

    @Transactional
    @Override
    @CachePut(value = "customerCache", key = "#id")
    public ResponseCustomerDTO update(Long id, RequestCustomerDTO requestCustomerDTO) {
        Customer customer = customerRepository.read(id)
                .orElseThrow(() -> new NotFoundException("Customer not found!"));
        customer.setName(requestCustomerDTO.name());
        customer.setSector(requestCustomerDTO.sector());
        customerRepository.save(customer);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                rabbitMQService.sendInvalidationEvent(id);
            }
        });

        return responseCustomerMapper.toDTO(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseCustomerDTO> findAll() {
        return responseCustomerMapper.toDTOs(customerRepository.readAll());

    }

    @Override
    public CurrentDTO getCurrent(UserDetails userDetails) {
       return new CurrentDTO(
               userDetails.getAuthorities(),
               userDetails.isAccountNonExpired(),
               userDetails.isAccountNonLocked(),
               userDetails.isEnabled(),
               userDetails.getUsername()
       );
    }
}
