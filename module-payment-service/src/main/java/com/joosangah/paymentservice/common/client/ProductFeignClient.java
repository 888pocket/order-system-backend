package com.joosangah.paymentservice.common.client;

import com.joosangah.paymentservice.common.domain.ProductResponse;
import com.joosangah.paymentservice.common.domain.ProductStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ProductFeignClient", url = "http://product-service:8081/product", configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);


    @GetMapping("/{productId}/stock")
    int getStock(@PathVariable Long productId);

    @PutMapping("/{productId}/restore-stock")
    void restoreStock(@PathVariable Long productId, @RequestBody ProductStockRequest request);

    @PutMapping("/{productId}/reduce-stock")
    void reduceStock(@PathVariable Long productId, @RequestBody ProductStockRequest request);
}
