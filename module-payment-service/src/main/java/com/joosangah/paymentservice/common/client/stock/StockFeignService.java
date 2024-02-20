package com.joosangah.paymentservice.common.client.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockFeignService {

    private final StockFeignClient stockFeignClient;

    public int getStock(Long productId) {
        return stockFeignClient.getStock(productId);
    }

    public void restoreStock(Long productId, int stock) {
        stockFeignClient.restoreStock(productId, new StockRequest(stock));
    }

    public void reduceStock(Long productId, int stock) {
        stockFeignClient.reduceStock(productId, new StockRequest(stock));
    }
}
