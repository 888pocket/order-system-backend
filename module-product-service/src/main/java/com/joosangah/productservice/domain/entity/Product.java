package com.joosangah.productservice.domain.entity;

import com.joosangah.productservice.common.domain.AuditEntity;
import com.joosangah.productservice.domain.enums.ProductType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    private LocalDateTime openAt;

    @Column(nullable = false)
    private Integer stock = 0;

    @Builder
    public Product(String name, ProductType type, LocalDateTime openAt, Integer stock) {
        this.name = name;
        this.type = type;
        this.openAt = openAt;
        this.stock = stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
