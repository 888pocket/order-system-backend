package com.joosangah.productservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joosangah.productservice.domain.enums.ProductType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private Integer stock;
    private ProductType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openAt;
}
