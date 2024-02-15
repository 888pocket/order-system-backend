package com.joosangah.paymentservice.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentRequest {

    private Long productId;
    private Integer amount;
}
