package com.grepp.coffee.web.order.controller;

import com.grepp.coffee.domain.orderItem.OrderItem;
import com.grepp.coffee.domain.order.OrderService;
import com.grepp.coffee.domain.orderItem.OrderItemResponseDTO;
import com.grepp.coffee.web.order.form.OrderItemEditForm;
import com.grepp.coffee.web.order.form.OrderItemSaveForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderService orderService;

    // orderItem 추가
    @PostMapping("/add")
    public ResponseEntity<?> addOrderItem(@Validated @RequestBody OrderItemSaveForm saveForm, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body("입력값 검증 실패");
            }
            OrderItemResponseDTO orderItem = orderService.addOrderItem(saveForm);
            return ResponseEntity.ok(orderItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문상품 추가중 서버 에러 발생");
        }
    }

    // orderItem 목록조회(order 이용)
    @GetMapping("/list/{orderId}")
    public ResponseEntity<?> readOrderItemList(@PathVariable("orderId") UUID orderId) {
        try {
            List<OrderItemResponseDTO> orderItemByOrderId = orderService.findOrderItemByOrderId(orderId);
            return ResponseEntity.ok(orderItemByOrderId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 orderId로 조회할 주문상품이 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 상품 조회중 서버 에러 발생");
        }
    }


    // orderItem 하나 조회(orderItemId 이용)
    @GetMapping("/read/{orderItemId}")
    public ResponseEntity<?> readOrderItem(@PathVariable("orderItemId") Long orderItemId) {
        try {
            OrderItemResponseDTO byOrderItemId = orderService.findByOrderItemId(orderItemId);
            return ResponseEntity.ok(byOrderItemId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회할 주문 상품을 찾지 못했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문상품 조회중 서버 에러 발생");
        }
    }

    // orderItem 수정
    @PutMapping("/update")
    public ResponseEntity<?> updateOrderItem(@Validated @RequestBody OrderItemEditForm editForm, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }
            OrderItemResponseDTO orderItem = orderService.updateOrderItem(editForm);
            return ResponseEntity.ok(orderItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 주문 상품을 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 상품 수정중 서버에러 발생");
        }
    }


    // orderItem 삭제
    @PutMapping("/delete/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable("orderItemId") Long orderItemId) {
        try {
            orderService.orderItemDelete(orderItemId);
            return ResponseEntity.ok("주문상품 삭제완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제하려는 주문상품이 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 상품 삭제중 에러 발생");
        }
    }
}
