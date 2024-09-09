package com.grepp.coffee.web.order.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemSaveForm {
    @NotNull
    private UUID orderId;

    @NotNull
    private UUID productId;

    @NotNull
    private int quantity;

}
