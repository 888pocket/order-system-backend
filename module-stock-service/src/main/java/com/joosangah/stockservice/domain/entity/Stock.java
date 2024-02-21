package com.joosangah.stockservice.domain.entity;

import com.joosangah.stockservice.common.domain.AuditEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stocks")
@Getter
@NoArgsConstructor
public class Stock extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;
    private int stock;
    @Column(name = "init_stock")
    private int initStock;

    @Builder
    public Stock(Long productId, int stock, int initStock) {
        this.productId = productId;
        this.stock = stock;
        this.initStock = initStock;
    }

    public void plus(int amount) {
        this.stock += amount;
    }

    public void minus(int amount) {
        this.stock -= amount;
    }
}
