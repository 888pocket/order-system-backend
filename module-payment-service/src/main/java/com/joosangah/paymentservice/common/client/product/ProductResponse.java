package com.joosangah.paymentservice.common.client.product;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private LocalDateTime openAt;
}
