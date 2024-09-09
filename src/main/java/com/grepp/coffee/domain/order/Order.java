package com.grepp.coffee.domain.order;


import com.grepp.coffee.domain.orderItem.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "postcode", nullable = false, length = 200)
    private String postcode;

    @Column(name = "order_status", nullable = false, length = 50)
    private String orderStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

}
