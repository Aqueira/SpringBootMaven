package com.example.order.data;

import com.example.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT order FROM Order order LEFT JOIN FETCH order.lineItems linetems WHERE order.id = :id")
    Optional<Order> read(@Param("id") Long id);

    @Query("SELECT order FROM Order order LEFT JOIN FETCH order.lineItems")
    List<Order> readAll();
}
