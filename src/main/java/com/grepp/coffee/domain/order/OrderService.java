package com.grepp.coffee.domain.order;

import com.grepp.coffee.domain.orderItem.OrderItem;
import com.grepp.coffee.domain.orderItem.OrderItemRepository;
import com.grepp.coffee.domain.orderItem.OrderItemResponseDTO;
import com.grepp.coffee.domain.product.Product;
import com.grepp.coffee.domain.product.ProductService;
import com.grepp.coffee.web.order.form.OrderEditForm;
import com.grepp.coffee.web.order.form.OrderItemEditForm;
import com.grepp.coffee.web.order.form.OrderItemSaveForm;
import com.grepp.coffee.web.order.form.OrderSaveForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;

    // order 추가
    public Order addOrder(OrderSaveForm saveForm) {
        Order order = new Order();

        order.setOrderId(UUID.randomUUID());
        order.setEmail(saveForm.getEmail());
        order.setAddress(saveForm.getAddress());
        order.setPostcode(saveForm.getPostcode());
        order.setOrderStatus(OrderStatus.ORDER_PLACED.getDescription());
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // 특정 order 하나 조회
    public OrderResponseDTO findByOrderId(UUID orderId) {
        Order findOrder = orderRepository.findByOrderId(orderId);
        if (findOrder == null) {
            throw new EntityNotFoundException("주문을 찾을 수 없음");
        }
        OrderResponseDTO result = new OrderResponseDTO(
                findOrder.getOrderId(),
                findOrder.getEmail(),
                findOrder.getAddress(),
                findOrder.getPostcode(),
                findOrder.getOrderStatus(),
                findOrder.getCreatedAt(),
                findOrder.getUpdatedAt(),
                findOrder.getOrderItems().stream().map(orderItem -> new OrderItemResponseDTO(
                        orderItem.getSeq(),
                        orderItem.getOrder().getOrderId(),
                        orderItem.getProduct().getProductId(),
                        orderItem.getProduct().getProductName(),
                        orderItem.getCategory(),
                        orderItem.getPrice(),
                        orderItem.getQuantity(),
                        orderItem.getCreatedAt(),
                        orderItem.getUpdatedAt()
                )).collect(Collectors.toList())
        );

        return result;
    }

    // order 수정
    public OrderResponseDTO orderUpdate(OrderEditForm editForm) {
        Order findOrder = orderRepository.findByOrderId(editForm.getOrderId());
        if (findOrder == null) {
            throw new EntityNotFoundException("수정할 주문이 없습니다.");
        }

        findOrder.setUpdatedAt(LocalDateTime.now());

        if (editForm.getAddress() != null) {
            findOrder.setAddress(editForm.getAddress());
        }
        if (editForm.getEmail() != null) {
            findOrder.setAddress(editForm.getEmail());
        }
        if (editForm.getPostcode() != null) {
            findOrder.setAddress(editForm.getPostcode());
        }
        if (editForm.getOrderStatus() != null) {
            findOrder.setAddress(editForm.getOrderStatus());
        }
        Order savedOrder = orderRepository.save(findOrder);

        return findByOrderId(savedOrder.getOrderId());
    }

    // oder 삭제
    public void orderDelete(UUID orderId) {

        Order findOrder = orderRepository.findByOrderId(orderId);
        if (findOrder == null) {
            throw new EntityNotFoundException("삭제하려는 주문이 없습니다.");
        }
        try {
            orderRepository.delete(findOrder);
            log.info("삭제 성공 order = {}", findOrder);
        } catch (Exception e) {
            log.info("삭제중 실패");
            throw e;
        }
    }


    // orderItem 추가
    public OrderItemResponseDTO addOrderItem(OrderItemSaveForm saveForm) {
        OrderItem orderItem = new OrderItem();
        Product findProduct = productService.findById(saveForm.getProductId());

        orderItem.setOrder(orderRepository.findByOrderId(saveForm.getOrderId()));
        orderItem.setProduct(productService.findById(saveForm.getProductId()));
        orderItem.setPrice(findProduct.getPrice());
        orderItem.setQuantity(saveForm.getQuantity());
        orderItem.setCategory(findProduct.getCategory());
        orderItem.setCreatedAt(LocalDateTime.now());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        OrderItemResponseDTO responseDTO = getOrderItemResponseDTO(savedOrderItem);

        return responseDTO;
    }

    // orderItem -> orderItemResponseDTO 로 변환 메서드
    private OrderItemResponseDTO getOrderItemResponseDTO(OrderItem savedOrderItem) {
        OrderItemResponseDTO responseDTO = new OrderItemResponseDTO();

        responseDTO.setSeq(savedOrderItem.getSeq());
        responseDTO.setOrderId(savedOrderItem.getOrder().getOrderId());
        responseDTO.setProductId(savedOrderItem.getProduct().getProductId());
        responseDTO.setPrice(savedOrderItem.getPrice());
        responseDTO.setQuantity(savedOrderItem.getQuantity());
        responseDTO.setCategory(savedOrderItem.getCategory());
        responseDTO.setCreatedAt(savedOrderItem.getCreatedAt());
        responseDTO.setUpdatedAt(savedOrderItem.getUpdatedAt());
        responseDTO.setProductName(savedOrderItem.getProduct().getProductName());
        return responseDTO;
    }

    // orderItem 조회
    public OrderItemResponseDTO findByOrderItemId(Long id) {
        OrderItem findOrderItem = orderItemRepository.findBySeq(id);
        if (findOrderItem == null) {
            throw new EntityNotFoundException("조회한 주문 상품이 존재하지 않습니다.");
        }
        OrderItemResponseDTO responseDTO = new OrderItemResponseDTO();

        responseDTO.setSeq(findOrderItem.getSeq());
        responseDTO.setOrderId(findOrderItem.getOrder().getOrderId());
        responseDTO.setProductId(findOrderItem.getProduct().getProductId());
        responseDTO.setProductName(findOrderItem.getProduct().getProductName());
        responseDTO.setQuantity(findOrderItem.getQuantity());
        responseDTO.setCategory(findOrderItem.getCategory());
        responseDTO.setCreatedAt(findOrderItem.getCreatedAt());
        responseDTO.setUpdatedAt(findOrderItem.getUpdatedAt());
        responseDTO.setPrice(findOrderItem.getPrice());

        return responseDTO;
    }

    // orderItem 수정
    public OrderItemResponseDTO updateOrderItem(OrderItemEditForm editForm) {
        try {
            OrderItem findOrderItem = orderItemRepository.findBySeq(editForm.getSeq());
            findOrderItem.setUpdatedAt(LocalDateTime.now());

            if (editForm.getQuantity() != null) {
                findOrderItem.setQuantity((int) editForm.getQuantity());
            }

            OrderItem updatedOrderItem = orderItemRepository.save(findOrderItem);
            return getOrderItemResponseDTO(updatedOrderItem);

        } catch (EntityNotFoundException e) {
            log.info("찾으려고 하는 주문상품이 없음");
            throw e;
        } catch (Exception e) {
            log.info("주문 상품정보 수정중 예외 발생");
            throw e;
        }
    }


    // orderItem 삭제
    public void orderItemDelete(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findBySeq(orderItemId);
        if (findOrderItem == null) {
            throw new EntityNotFoundException("삭제하려 하는 주문 상품이 존재하지 않습니다.");
        }
        try {
            orderItemRepository.delete(findOrderItem);
        } catch (Exception e) {
            log.info("주문상품 삭제중 에러 발생");
            throw e;
        }

    }


    // 이메일로 order 목록 조회
    public List<OrderResponseDTO> findOrderByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);

        if (orders == null || orders.isEmpty()) {
            throw new EntityNotFoundException("해당 이메일로 주문한 이력이 없습니다.");
        }

        return orders.stream().map(order -> new OrderResponseDTO(
                order.getOrderId(),
                order.getEmail(),
                order.getAddress(),
                order.getPostcode(),
                order.getOrderStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getOrderItems().stream().map(orderItem -> new OrderItemResponseDTO(
                        orderItem.getSeq(),
                        orderItem.getOrder().getOrderId(),
                        orderItem.getProduct().getProductId(),
                        orderItem.getProduct().getProductName(),
                        orderItem.getCategory(),
                        orderItem.getPrice(),
                        orderItem.getQuantity(),
                        orderItem.getCreatedAt(),
                        orderItem.getUpdatedAt()
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }


    // orderId 로 주문 상품 목록 조회
    public List<OrderItemResponseDTO> findOrderItemByOrderId(UUID orderId) {
        try {
            Order findOrder = orderRepository.findByOrderId(orderId);
            List<OrderItem> findOrderItemList = orderItemRepository.findByOrder(findOrder);
            List<OrderItemResponseDTO> resultList = findOrderItemList.stream().map(orderItem -> new OrderItemResponseDTO(
                    orderItem.getSeq(),
                    orderItem.getOrder().getOrderId(),
                    orderItem.getProduct().getProductId(),
                    orderItem.getProduct().getProductName(),
                    orderItem.getCategory(),
                    orderItem.getPrice(),
                    orderItem.getQuantity(),
                    orderItem.getCreatedAt(),
                    orderItem.getCreatedAt()
            )).collect(Collectors.toList());

            return resultList;
        } catch (EntityNotFoundException e) {
            log.info("order로 주문 상품 조회중 해당 order의 주문 상품을 찾을 수 없음");
            throw e;
        } catch (Exception e) {
            log.info("order로 주문 상품 목록 조회중 알 수 없는 에러 발생");
            throw e;
        }
    }


}
