package com.joosangah.paymentservice.domain.entity;

import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void 생성_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        // when, then
        Assertions.assertThat(payment.getProductId()).isEqualTo(1L);
        Assertions.assertThat(payment.getUserId()).isEqualTo(1L);
        Assertions.assertThat(payment.getAmount()).isEqualTo(1);
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.START);
    }

    @Test
    void 결제_성공_업데이트_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        // when
        payment.success();

        // then
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.SUCCESS);
    }

    @Test
    void 결제_실패_업데이트_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        // when
        payment.fail();

        // then
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.FAIL);
    }

    @Test
    void 결제_취소_업데이트_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        // when
        payment.cancel();

        // then
        Assertions.assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCEL);
    }
}