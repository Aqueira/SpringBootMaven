package com.example.domains;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "deliver_to", nullable = false, length = 100)
    private String deliverTo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<LineItem> lineItems;

    public static class OrderBuilder {
        public Order build() {
            Order order = new Order(id, deliverTo, customer, lineItems);
            order.lineItems.forEach(lineItem -> lineItem.setOrder(order));
            return order;
        }
    }
}
