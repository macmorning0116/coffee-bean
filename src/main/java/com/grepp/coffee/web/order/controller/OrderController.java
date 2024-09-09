package com.grepp.coffee.web.order.controller;

import com.grepp.coffee.domain.order.Order;
import com.grepp.coffee.domain.order.OrderResponseDTO;
import com.grepp.coffee.domain.order.OrderService;
import com.grepp.coffee.web.order.form.OrderEditForm;
import com.grepp.coffee.web.order.form.OrderSaveForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    // order 추가
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@Validated @RequestBody OrderSaveForm saveForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("order 추가하다 검증 실패 = {}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Order savedOrder = orderService.addOrder(saveForm);
        if (savedOrder != null) {
            // 정상 추가 완료
            return ResponseEntity.ok(savedOrder);
        }
        // 추가하던중 에러발생
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("order 추가 중 알 수 없는 에러 발생");
    }

    // order 하나 조회
    @GetMapping("/read/{orderId}")
    public ResponseEntity<?> readOrder(@PathVariable("orderId") UUID orderId) { // 이렇게 단순 타입의 경우 Spring 이 입력값을 검증해줘서 만약 UUID 타입이 아닐경 400 Bad Request 에러로 처리해준다.
        try {
            OrderResponseDTO byOrderId = orderService.findByOrderId(orderId);
            return ResponseEntity.ok(byOrderId);
        } catch (EntityNotFoundException e) {
            log.info("orderId로 조회하는데 에러 발생");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 orderId로 조회할 order가 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 조회중 에러발생");
        }
    }

    // order 목록 조회 (이메일 사용)
    @GetMapping("/list/{email}")
    public ResponseEntity<?> readOrderList(@PathVariable("email") String email) {
        try {
            List<OrderResponseDTO> orderByEmail = orderService.findOrderByEmail(email);
            return ResponseEntity.ok(orderByEmail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 주문한 내역이 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일로 주문 목록 조회중 에러발생");
        }
    }

    // order 수정
    @PutMapping("/edit")
    public ResponseEntity<?> editOrder(@Validated @RequestBody OrderEditForm editForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("잘못된 입력값 에러");
        }
        try {
            OrderResponseDTO order = orderService.orderUpdate(editForm);
            return ResponseEntity.ok(order);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 주문이 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 수정중 서버 에러 발생");
        }
    }


    // order 삭제
    @PutMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") UUID orderId) {
        try {
            orderService.orderDelete(orderId);
            return ResponseEntity.ok("주문 삭제 성공");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 주문 조회 실패");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제중 서버 에러 발생");
        }
    }


}
