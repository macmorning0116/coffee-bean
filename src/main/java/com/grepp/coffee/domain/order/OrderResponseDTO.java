package com.grepp.coffee.domain.order;

import com.grepp.coffee.domain.orderItem.OrderItemResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDTO {

    private UUID orderId;
    private String email;
    private String address;
    private String postcode;
    private String orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDTO> orderItems;


    public OrderResponseDTO(UUID orderId, String email, String address, String postcode, String orderStatus,
                            LocalDateTime createdAt, LocalDateTime updatedAt, List<OrderItemResponseDTO> orderItems) {
        this.orderId = orderId;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = orderItems;
    }
}

