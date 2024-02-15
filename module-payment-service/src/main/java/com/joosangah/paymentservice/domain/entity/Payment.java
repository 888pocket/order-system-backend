package com.joosangah.paymentservice.domain.entity;

import com.joosangah.paymentservice.common.domain.AuditEntity;
import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import javax.persistence.Column;
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
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.START;

    @Builder
    public Payment(Long productId, Long userId, Integer amount) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void success() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void fail() {
        this.status = PaymentStatus.FAIL;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCEL;
    }
}
