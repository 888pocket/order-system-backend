package com.joosangah.productservice.service;

import com.joosangah.productservice.common.client.StockFeignService;
import com.joosangah.productservice.domain.dto.request.ProductRequest;
import com.joosangah.productservice.domain.dto.response.ProductResponse;
import com.joosangah.productservice.domain.entity.Product;
import com.joosangah.productservice.mapper.ProductResponseMapper;
import com.joosangah.productservice.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final StockFeignService stockFeignService;

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

    @Transactional
    public Long addProduct(ProductRequest request) {
        Product newProduct = productRepository.save(Product.builder()
                .name(request.getName())
                .openAt(request.getOpenAt())
                .type(request.getType()).build());

        stockFeignService.addStock(newProduct.getId(), request.getStock());

        return newProduct.getId();
    }

    public void validateProductForPurchase(Long productId) throws RequestAbortedException {
        Product product = loadProduct(productId);
        if(product.getOpenAt() != null && product.getOpenAt()
                .isAfter(LocalDateTime.now())) {
            throw new RequestAbortedException("Not purchasable");
        }
    }
}
