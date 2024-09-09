package com.grepp.coffee.domain.product;

import com.grepp.coffee.web.product.form.ProductEditForm;
import com.grepp.coffee.web.product.form.ProductSaveForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 하나 조회
    public Product findById(UUID productId) {
        Product byProductId = productRepository.findByProductId(productId);
        if (byProductId == null) {
            throw new EntityNotFoundException("상품이 존재하지 않습니다.");
        }
        return byProductId;
    }

    // 상품 전체 조회
    public List<Product> getAllList() {
        return productRepository.findAll();
    }

    // 상품 등록
    public Product addProduct(ProductSaveForm saveForm) {
        Product product = new Product();

        product.setProductId(UUID.randomUUID());
        product.setProductName(saveForm.getProductName());
        product.setCategory(saveForm.getCategory());
        product.setPrice(saveForm.getPrice());
        product.setDescription(saveForm.getDescription());
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    // 상품 정보 수정
    public Product edit(ProductEditForm form) {

        Product findProduct = findById(form.getProductId());
        if (findProduct == null) {
            return null;
        }
        findProduct.setUpdatedAt(LocalDateTime.now());

        if (form.getProductName() != null) {
            findProduct.setProductName(form.getProductName());
        }
        if (form.getDescription() != null) {
            findProduct.setDescription(form.getDescription());
        }
        if (form.getPrice() != null) {
            findProduct.setPrice(form.getPrice());
        }
        if (form.getCategory() != null) {
            findProduct.setCategory(form.getCategory());
        }

        return productRepository.save(findProduct);
    }


    // 상품 삭제
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findByProductId(productId);

        try {
        if (product == null) {
            log.info("상품을 찾는것에 실패했습니다 찾으려던 상품의 id = {}", productId);
            throw new EntityNotFoundException("해당 상품을 찾을 수 없음");
        }
        productRepository.delete(product);
        log.info("상품 삭제 성공 삭제된 상품 = {}", product);
        } catch (Exception e) {
            log.info("상품 삭제 실패", e);
            throw e;
        }

    }


}
