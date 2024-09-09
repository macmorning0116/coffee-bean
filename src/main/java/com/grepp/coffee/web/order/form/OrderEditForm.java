package com.grepp.coffee.web.order.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderEditForm {

    @NotNull
    private UUID orderId;

    private String email;

    private String address;

    private String postcode;

    private String orderStatus;

}
