package com.joosangah.productservice.common.client;

import com.joosangah.productservice.common.domain.StockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "STOCK-SERVICE", configuration = FeignClientConfiguration.class)
public interface StockFeignClient {

    @PostMapping("/stock/{productId}")
    void addStock(@PathVariable Long productId, @RequestBody StockRequest request);
}
