package com.joosangah.paymentservice.common.client;

import com.joosangah.paymentservice.common.domain.ProductResponse;
import com.joosangah.paymentservice.common.domain.ProductStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeignService {

    private final ProductFeignClient productFeignClient;

    public ProductResponse getProduct(Long productId) {
        return productFeignClient.getProduct(productId);
    }

    public int getStock(Long productId) {
        return productFeignClient.getStock(productId);
    }

    public void restoreStock(Long productId, int stock) {
        productFeignClient.restoreStock(productId, new ProductStockRequest(stock));
    }

    public void reduceStock(Long productId, int stock) {
        productFeignClient.reduceStock(productId, new ProductStockRequest(stock));
    }
}
