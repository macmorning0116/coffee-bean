package com.grepp.coffee.web.product;

import com.grepp.coffee.domain.product.Product;
import com.grepp.coffee.domain.product.ProductService;
import com.grepp.coffee.web.product.form.ProductEditForm;
import com.grepp.coffee.web.product.form.ProductSaveForm;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody ProductSaveForm productSaveForm, BindingResult bindingResult) {

        // 상품 등록 입력값 검증 통과x 400 Bad Request 반환
        if (bindingResult.hasErrors()) {
            log.info("add product bindingResult = {}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        // 검증 성공 200 ok 및 객체 반환 or 예외발생시 throw
        try {
            Product responseProduct = productService.addProduct(productSaveForm);
            return ResponseEntity.ok(responseProduct);
        } catch (Exception e) {
            throw new RuntimeException("상품 등록중 에러 발생", e);
        }
    }

    // 상품 수정
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@Validated @RequestBody ProductEditForm productEditForm, BindingResult bindingResult) {

        // 상품 수정 입력값 검증 통과x 400 Bad Request 반환
        if (bindingResult.hasErrors()) {
            log.info("edit product bindingResult = {}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // 검증 성공 200 ok 및 객체 반환 or 예외발생시 throw
        try {
            Product responseProduct = productService.edit(productEditForm);
            return ResponseEntity.ok(responseProduct);
        } catch (Exception e) {
            throw new RuntimeException("상품 수정중 에러 발생", e);
        }
    }

    // 상품리스트 조회
    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Product> productList;
        try {
            productList = productService.getAllList();
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            throw new RuntimeException("상품 리스트 조회중 에러 발생");
        }
    }


    // 상품 삭제
    @PutMapping("/delete/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") UUID productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            // 상품을 찾지 못하였을때
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품을 찾을 수 없습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 삭제 중 오류 발생");
        }
    }


}
