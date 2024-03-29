package com.joosangah.productservice.domain.dto.response;

import com.joosangah.productservice.domain.enums.ProductType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private ProductType type;
    private LocalDateTime openAt;
}
