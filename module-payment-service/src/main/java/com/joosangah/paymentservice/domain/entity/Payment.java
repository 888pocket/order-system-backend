package com.joosangah.paymentservice.domain.entity;

import com.joosangah.paymentservice.common.domain.AuditEntity;
import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@NoArgsConstructor
public class Payment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Builder
    public Payment(Long productId, Long userId, Integer amount, PaymentStatus status) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
    }
}
