package com.grepp.coffee.web.product.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductEditForm {

    @NotNull
    private UUID productId;

    private String productName;

    private String category;

    private Long price;

    private String description;

}
