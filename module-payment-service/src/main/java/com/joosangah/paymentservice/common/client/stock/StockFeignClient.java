package com.joosangah.paymentservice.common.client.stock;

import com.joosangah.paymentservice.common.client.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "StockFeignClient", url = "http://stock-service:8083/stock", configuration = FeignClientConfiguration.class)
public interface StockFeignClient {

    @PostMapping("/{productId}")
    void addStock(@PathVariable Long productId, @RequestBody StockRequest request);

    @GetMapping("/{productId}/stock")
    int getStock(@PathVariable Long productId);

    @PutMapping("/{productId}/restore-stock")
    void restoreStock(@PathVariable Long productId, @RequestBody StockRequest request);

    @PutMapping("/{productId}/reduce-stock")
    void reduceStock(@PathVariable Long productId, @RequestBody StockRequest request);
}
