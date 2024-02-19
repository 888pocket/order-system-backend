package com.joosangah.productservice.domain.dto.request;

import com.joosangah.productservice.domain.enums.ProductType;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {

    private String name;
    private ProductType type;
    private LocalDateTime openAt;
    @Min(0)
    private Integer stock;
}
