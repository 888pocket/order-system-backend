package com.joosangah.productservice.service;

import com.joosangah.productservice.domain.dto.response.ProductResponse;
import com.joosangah.productservice.domain.entity.Product;
import com.joosangah.productservice.mapper.ProductResponseMapper;
import com.joosangah.productservice.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductResponseMapper productResponseMapper;

    private Product loadProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    public ProductResponse loadProductResponse(Long productId) {
        return productResponseMapper.toDto(loadProduct(productId));
    }

    public List<ProductResponse> loadProductResponses() {
        return productResponseMapper.toDtoList(productRepository.findAll());
    }

    @Cacheable(value = "stock", key = "#productId")
    public int getStock(Long productId) {
        return loadProduct(productId).getStock();
    }

    @Transactional
    @CachePut(value = "stock", key = "#productId")
    public int updateStock(Long productId, int newStock) {
        Product product = loadProduct(productId);
        product.setStock(newStock);
        productRepository.save(product);

        return newStock;
    }
}
