package com.example.customer.data;


import com.example.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT DISTINCT customer " +
            "FROM Customer customer " +
            "LEFT JOIN FETCH customer.orders order " +
            "WHERE customer.id = :id")
    Optional<Customer> read(@Param("id") Long id);

    @Query("SELECT customer FROM Customer customer LEFT JOIN FETCH customer.orders orders")
    List<Customer> readAll();
}
