package com.joosangah.stockservice.service;

import com.joosangah.stockservice.domain.entity.Stock;
import com.joosangah.stockservice.repository.StockRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public Stock loadStock(Long productId) {
        return stockRepository.findByProductId(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Cacheable(value = "stock", key = "#productId")
    public int getStock(Long productId) {
        return loadStock(productId).getStock();
    }

    @Transactional
    @CachePut(value = "stock", key = "#productId")
    public int addStock(Long productId, int newStock) {
        Stock stock = Stock.builder()
                .productId(productId)
                .stock(newStock)
                .initStock(newStock).build();
        stockRepository.save(stock);

        return stock.getStock();
    }

    @Transactional
    @CachePut(value = "stock", key = "#productId")
    public int restoreStock(Long productId, int difference) {
        Stock stock = loadStock(productId);
        stock.plus(difference);
        if (stock.getStock() > stock.getInitStock()) {
            throw new RuntimeException("exceeded limit of stock");
        }

        return stock.getStock();
    }

    @Transactional
    @CachePut(value = "stock", key = "#productId")
    public int reduceStock(Long productId, int difference) {
        Stock stock = loadStock(productId);
        stock.minus(difference);
        if (stock.getStock() < 0) {
            throw new RuntimeException("out of stock");
        }

        return stock.getStock();
    }
}
