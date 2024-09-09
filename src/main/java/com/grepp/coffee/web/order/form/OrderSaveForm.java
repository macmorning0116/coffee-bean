package com.grepp.coffee.web.order.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderSaveForm {

    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String postcode;

}
