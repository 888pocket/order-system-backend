package com.joosangah.stockservice.common.client;

import com.joosangah.stockservice.common.domain.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping("/product/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);
}
