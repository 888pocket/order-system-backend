package com.joosangah.paymentservice.service;

import com.joosangah.paymentservice.common.client.product.ProductFeignService;
import com.joosangah.paymentservice.common.client.stock.StockFeignService;
import com.joosangah.paymentservice.common.client.user.User;
import com.joosangah.paymentservice.common.util.EventTrigger;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.domain.entity.Payment;
import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import com.joosangah.paymentservice.repository.PaymentRepository;
import java.util.NoSuchElementException;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private StockFeignService stockFeignService;

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment loadPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Long startPayment(User user, PaymentRequest request) throws RequestAbortedException {
        // 재고, 오픈 일정 체크
        validateProductForPurchase(request.getProductId(), request.getAmount());

        Payment payment = Payment.builder()
                .productId(request.getProductId())
                .userId(user.getId())
                .amount(request.getAmount()).build();

        return paymentRepository.save(payment).getId();
    }

    public void validateProductForPurchase(Long productId, int amount)
            throws RequestAbortedException {
        // 오픈 일정 체크
        productFeignService.validateProductForPurchase(productId);

        // 재고 체크
        if (stockFeignService.getStock(productId) < amount) {
            throw new RequestAbortedException("out of stock");
        }
    }

    public void executePayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isPaymentUpdatable(user, payment)) {
            throw new RuntimeException("can't execute payment");
        }

        if (!tryRandomSuccess(payment)) {
            throw new RuntimeException("failed");
        }
    }

    @Transactional
    boolean tryRandomSuccess(Payment payment) {
        // 20% 확률로 실패
        if (EventTrigger.isEventTriggered(20)) {
            failPayment(payment);
            return false;
        }

        successPayment(payment);
        return true;
    }

    boolean isPaymentUpdatable(User user, Payment payment) {
        if (payment.getStatus() != PaymentStatus.START
                || user.getId() != payment.getUserId()) {
            return false;
        }

        return true;
    }

    @Transactional
    public void failPayment(Payment payment) {
        payment.fail();
        paymentRepository.save(payment);

        // 재고 복구
        stockFeignService.restoreStock(payment.getProductId(), payment.getAmount());
    }

    @Transactional
    public void successPayment(Payment payment) {
        payment.success();
        paymentRepository.save(payment);

        // 재고 감소
        stockFeignService.reduceStock(payment.getProductId(), payment.getAmount());
    }

    @Transactional
    public void cancelPayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isPaymentUpdatable(user, payment)) {
            throw new RuntimeException("can't cancel payment");
        }

        // 재고 복구
        stockFeignService.restoreStock(payment.getProductId(), payment.getAmount());

        payment.cancel();
        paymentRepository.save(payment);
    }
}
