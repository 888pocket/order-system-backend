package com.joosangah.productservice.common.client;

import com.joosangah.productservice.common.domain.StockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockFeignService {

    private final StockFeignClient stockFeignClient;

    public void addStock(Long productId, int stock) {
        stockFeignClient.addStock(productId, StockRequest.builder().stock(stock).build());
    }
}
