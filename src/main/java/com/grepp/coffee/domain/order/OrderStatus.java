package com.grepp.coffee.domain.order;

public enum OrderStatus {

    ORDER_PLACED("주문완료"),
    SHIPPED("배송시작"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료"),
    CANCELED("주문취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
