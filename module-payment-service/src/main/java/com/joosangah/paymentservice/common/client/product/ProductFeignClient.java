package com.joosangah.paymentservice.common.client.product;

import com.joosangah.paymentservice.common.client.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping("/product/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);

    @GetMapping("/product/{productId}/validate")
    void validateProductForPurchase(@PathVariable Long productId);
}
