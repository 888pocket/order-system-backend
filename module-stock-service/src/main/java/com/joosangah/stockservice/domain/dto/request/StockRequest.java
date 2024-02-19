package com.joosangah.stockservice.domain.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {

    @Min(0)
    private int stock;
}
