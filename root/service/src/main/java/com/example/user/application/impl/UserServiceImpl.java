package com.example.user.application.impl;

import com.example.DTOs.userdto.RequestUserDTO;
import com.example.DTOs.userdto.ResponseUserDTO;
import com.example.configurations.rabbitmq.RabbitMQService;
import com.example.contracts.UserService;
import com.example.domains.User;
import com.example.exceptions.NotFoundException;
import com.example.user.data.UserRepository;
import lombok.RequiredArgsConstructor;

import com.example.mappers.userMappers.RequestUserMapper;
import com.example.mappers.userMappers.ResponseUserMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RequestUserMapper requestUserMapper;
    private final ResponseUserMapper responseUserMapper;
    private final RabbitMQService rabbitMQService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public ResponseUserDTO create(RequestUserDTO requestUserDTO) {
        User user = requestUserMapper.toEntity(requestUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return responseUserMapper.toDTO(userRepository.save(user));
    }

    @Cacheable(value = "userCache", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public ResponseUserDTO read(Long id) {
        return userRepository.read(id)
                .map(responseUserMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                rabbitMQService.sendInvalidationEvent(id);
            }
        });
    }

    @Transactional
    @Override
    @CachePut(value = "userCache", key = "#id")
    public ResponseUserDTO update(Long id, RequestUserDTO requestUserDTO) {
        User user = userRepository.read(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        user.setUsername(requestUserDTO.username());
        user.setPassword(passwordEncoder.encode(requestUserDTO.password()));
        userRepository.save(user);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                rabbitMQService.sendInvalidationEvent(id);
            }
        });

        return responseUserMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseUserDTO> readAll() {
        return responseUserMapper.toDTOs(userRepository.readAll());
    }
}

