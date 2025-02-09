package com.example.configurations.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class RabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendInvalidationEvent(Long userId) {
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.EXCHANGE_NAME, "", userId);
    }
}
