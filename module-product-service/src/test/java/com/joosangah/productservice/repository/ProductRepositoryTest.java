package com.joosangah.productservice.repository;

import com.joosangah.productservice.domain.entity.Product;
import com.joosangah.productservice.domain.enums.ProductType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 데이터_삽입_테스트() {
        List<Product> newProducts = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            newProducts.add(Product.builder()
                    .name("(🔥)test product " + i)
                    .type(ProductType.HOT_DEAL)
                    .openAt(LocalDateTime.now().plusMinutes(10)).build());
        }

        for (int i = 0; i < 8; i++) {
            newProducts.add(Product.builder()
                    .name("test product " + i)
                    .type(ProductType.DEFAULT).build());
        }

        productRepository.saveAll(newProducts);
    }
}