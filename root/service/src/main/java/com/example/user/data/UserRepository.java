package com.example.user.data;

import com.example.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user JOIN FETCH user.customer WHERE user.id = :id")
    Optional<User> read(@Param("id") Long id);

    @Query("SELECT user FROM User user JOIN FETCH user.customer")
    List<User> readAll();

    Optional<User> findByUsername(String username);
}
