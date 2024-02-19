package com.joosangah.stockservice.common.client;

import com.joosangah.stockservice.common.domain.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeignService {

    private final ProductFeignClient productFeignClient;

    public ProductResponse getProduct(Long productId) {
        return productFeignClient.getProduct(productId);
    }
}
