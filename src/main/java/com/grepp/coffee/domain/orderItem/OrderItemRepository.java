package com.grepp.coffee.domain.orderItem;

import com.grepp.coffee.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findBySeq(Long orderItemId);

    List<OrderItem> findByOrder(Order order);

}
