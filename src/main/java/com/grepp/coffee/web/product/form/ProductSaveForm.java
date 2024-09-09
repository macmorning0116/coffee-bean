package com.grepp.coffee.web.product.form;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSaveForm {

    @NotBlank
    private String productName;

    @NotBlank
    private String category;

    @NotNull
    private Long price;

    @NotBlank
    private String description;

}
