package com.joosangah.paymentservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.joosangah.paymentservice.common.client.product.ProductFeignService;
import com.joosangah.paymentservice.common.client.stock.StockFeignService;
import com.joosangah.paymentservice.common.client.user.User;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.domain.entity.Payment;
import com.joosangah.paymentservice.repository.PaymentRepository;
import feign.FeignException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private ProductFeignService productFeignService;
    @Mock
    private StockFeignService stockFeignService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void 결제_로드_성공_테스트() {
        // given
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(1).build();

        when(paymentRepository.findById(anyLong()))
                .thenReturn(Optional.of(payment));

        // when
        Payment result = paymentService.loadPayment(anyLong());

        // then
        Assertions.assertThat(result.getId()).isEqualTo(payment.getId());
    }

    @Test
    void 결제_로드_실패_테스트() {
        // given
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThatThrownBy(() -> paymentService.loadPayment(anyLong()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 결제_시작_성공_테스트() throws RequestAbortedException {
        // given
        int amount = 2;
        Payment payment = Payment.builder()
                .productId(1L)
                .userId(1L)
                .amount(amount).build();
        User user = User.builder().id(1L).build();
        PaymentRequest request = PaymentRequest.builder().amount(amount).productId(1L).build();

        doNothing().when(productFeignService).validateProductForPurchase(anyLong());
        when(stockFeignService.getStock(anyLong())).thenReturn(amount + 1);
        when(paymentRepository.save(any())).thenReturn(payment);

        // when, then
        Assertions.assertThat(paymentService.startPayment(user, request))
                .isEqualTo(payment.getId());
    }

    @Test
    void 결제_시작_재고부족_실패_테스트() {
        // given
        int amount = 2;
        User user = User.builder().id(1L).build();
        PaymentRequest request = PaymentRequest.builder().amount(amount).productId(1L).build();

        doNothing().when(productFeignService).validateProductForPurchase(anyLong());
        when(stockFeignService.getStock(anyLong())).thenReturn(amount - 1); // 재고 부족

        // when, then
        Assertions.assertThatThrownBy(() -> paymentService.startPayment(user, request))
                .isInstanceOf(RequestAbortedException.class);
    }

    @Test
    void 결제_시작_오픈일정_실패_테스트() {
        // given
        int amount = 2;
        User user = User.builder().id(1L).build();
        PaymentRequest request = PaymentRequest.builder().amount(amount).productId(1L).build();

        doThrow(FeignException.class).when(productFeignService)
                .validateProductForPurchase(anyLong());

        // when, then
        Assertions.assertThatThrownBy(() -> paymentService.startPayment(user, request))
                .isInstanceOf(FeignException.class);
    }

    @Test
    void 결제_실행_성공_테스트() {

    }

    @Test
    void 결제_실행_상태_실패_테스트() {

    }

    @Test
    void 결제_실행_유저_불일치_실패_테스트() {

    }

    @Test
    void 결제_실행_랜덤_실패_테스트() {

    }

    @Test
    void 결제_취소_성공_테스트() {

    }

    @Test
    void 결제_취소_상태_실패_테스트() {

    }

    @Test
    void 결제_취소_재고_복구_실패_테스트() {

    }
}