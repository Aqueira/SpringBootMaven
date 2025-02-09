package com.example.domains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
@Table(name = "line_items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ToString.Exclude
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;
}
