package com.joosangah.paymentservice.common.client.stock;

import com.joosangah.paymentservice.common.client.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "STOCK-SERVICE", configuration = FeignClientConfiguration.class)
public interface StockFeignClient {

    @PostMapping("/stock/{productId}")
    void addStock(@PathVariable Long productId, @RequestBody StockRequest request);

    @GetMapping("/stock/public/{productId}")
    int getStock(@PathVariable Long productId);

    @PutMapping("/stock/{productId}/restore")
    void restoreStock(@PathVariable Long productId, @RequestBody StockRequest request);

    @PutMapping("/stock/{productId}/reduce")
    void reduceStock(@PathVariable Long productId, @RequestBody StockRequest request);
}
