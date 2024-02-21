package com.joosangah.paymentservice.common.client.product;

import com.joosangah.paymentservice.common.client.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductFeignClient", url = "http://product-service:8081/product", configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);

    @GetMapping("/{productId}/validate")
    void validateProductForPurchase(@PathVariable Long productId);
}
