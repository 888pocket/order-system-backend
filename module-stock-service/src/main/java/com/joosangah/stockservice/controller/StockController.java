package com.joosangah.stockservice.controller;

import com.joosangah.stockservice.common.client.ProductFeignService;
import com.joosangah.stockservice.domain.dto.request.StockRequest;
import com.joosangah.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.execchain.RequestAbortedException;
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
    public void restoreStock(@PathVariable Long productId,
            @RequestBody StockRequest request) throws RequestAbortedException {
        stockService.plusStock(productId, request.getStock());
    }

    @PutMapping("/{productId}/reduce")
    public void reduceStock(@PathVariable Long productId,
            @RequestBody StockRequest request) throws RequestAbortedException {
        stockService.minusStock(productId,
                request.getStock());
    }
}
