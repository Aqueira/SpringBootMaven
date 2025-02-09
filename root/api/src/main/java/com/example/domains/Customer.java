package com.example.domains;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String sector;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;
}
