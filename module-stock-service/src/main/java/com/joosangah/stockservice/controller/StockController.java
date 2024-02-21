package com.joosangah.stockservice.controller;

import com.joosangah.stockservice.common.client.ProductFeignService;
import com.joosangah.stockservice.domain.dto.request.StockRequest;
import com.joosangah.stockservice.service.StockService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final ProductFeignService productFeignService;

    private final RedissonClient redissonClient;

    @GetMapping("/public/{productId}")
    public int loadStock(@PathVariable Long productId) {
        return stockService.getStock(productId);
    }

    @PostMapping("/{productId}")
    public void addStock(@PathVariable Long productId,
            @RequestBody StockRequest request) {
        productFeignService.getProduct(productId);
        stockService.addStock(productId, request.getStock());
    }

    @PutMapping("/{productId}/restore")
    public int restoreStock(@PathVariable Long productId, @RequestBody StockRequest request) {
        RLock lock = redissonClient.getLock(String.format("stock:product:%d", productId));
        try {
            boolean available = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("redisson getLock timeout");
            }

            return stockService.restoreStock(productId, request.getStock());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @PutMapping("/{productId}/reduce")
    public int reduceStock(@PathVariable Long productId, @RequestBody StockRequest request) {
        RLock lock = redissonClient.getLock(String.format("stock:product:%d", productId));
        try {
            boolean available = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("redisson getLock timeout");
            }

            return stockService.reduceStock(productId, request.getStock());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
