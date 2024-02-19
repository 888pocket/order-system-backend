package com.joosangah.stockservice.common.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private Integer stock;
    private LocalDateTime openAt;
}
