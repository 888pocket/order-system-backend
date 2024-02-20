package com.joosangah.paymentservice.repository;

import com.joosangah.paymentservice.domain.entity.Payment;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void 결제_생성_테스트() {
        // given
        Payment payment1 = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();
        Payment payment2 = Payment.builder()
                .productId(1L)
                .userId(2L)
                .amount(1).build();

        // when
        Payment result1 = paymentRepository.save(payment1);
        Payment result2 = paymentRepository.save(payment2);

        // then
        Assertions.assertThat(payment1.getUserId()).isEqualTo(result1.getUserId());
        Assertions.assertThat(payment2.getUserId()).isEqualTo(result2.getUserId());
    }

    @Test
    void 결제_로드_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        // when
        Payment result = paymentRepository.save(payment);

        // then
        Assertions.assertThat(result.getUserId()).isEqualTo(result.getUserId());
    }

    @Test
    void 결제_리스트_로드_테스트() {
        // given
        List<Payment> payments = Arrays.asList(Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build(), Payment.builder()
                .productId(1L)
                .userId(2L)
                .amount(1).build());

        // when
        List<Payment> result = paymentRepository.saveAll(payments);

        // then
        Assertions.assertThat(result.size()).isEqualTo(payments.size());
    }
}