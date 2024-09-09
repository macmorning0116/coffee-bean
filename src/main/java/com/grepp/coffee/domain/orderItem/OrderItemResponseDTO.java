package com.grepp.coffee.domain.orderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDTO {

    private Long seq;

    private UUID orderId;

    private UUID productId;

    private String productName;

    private String category;

    private Long price;

    private int quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
