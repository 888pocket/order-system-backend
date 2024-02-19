package com.joosangah.productservice.common.client;

import com.joosangah.productservice.common.domain.StockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "StockFeignClient", url = "http://stock-service:8083/stock", configuration = FeignClientConfiguration.class)
public interface StockFeignClient {

    @PostMapping("/{productId}")
    void addStock(@PathVariable Long productId, @RequestBody StockRequest request);
}
