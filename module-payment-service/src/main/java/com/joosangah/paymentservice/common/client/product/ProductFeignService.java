package com.joosangah.paymentservice.common.client.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeignService {

    private final ProductFeignClient productFeignClient;

    public void validateProductForPurchase(Long productId) {
        productFeignClient.validateProductForPurchase(productId);
    }
}
