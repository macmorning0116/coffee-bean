package com.grepp.coffee.web.order.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderItemEditForm {

    @NotNull
    private Long seq;

    private Integer quantity;

}
