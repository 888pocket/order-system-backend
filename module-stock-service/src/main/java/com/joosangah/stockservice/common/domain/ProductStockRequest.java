package com.joosangah.stockservice.common.domain;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockRequest {

    @Min(0)
    private int stock;
}
