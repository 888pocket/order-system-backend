package com.joosangah.paymentservice.service;

import com.joosangah.paymentservice.common.client.ProductFeignService;
import com.joosangah.paymentservice.common.domain.ProductResponse;
import com.joosangah.paymentservice.common.domain.User;
import com.joosangah.paymentservice.common.util.EventTrigger;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.domain.entity.Payment;
import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import com.joosangah.paymentservice.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProductFeignService productFeignService;

    private final PaymentRepository paymentRepository;

    public Payment loadPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void startPayment(User user, PaymentRequest request) {
        Payment payment = Payment.builder()
                .productId(request.getProductId())
                .userId(user.getId())
                .amount(request.getAmount()).build();

        productFeignService.reduceStock(payment.getProductId(), payment.getAmount());
        paymentRepository.save(payment);
    }

    public void validateProductForPurchase(Long productId, int stock)
            throws RequestAbortedException {
        ProductResponse product = productFeignService.getProduct(productId);
        if (!hasEnoughStock(productId, stock)
                || (product.getOpenAt() != null && product.getOpenAt()
                .isAfter(LocalDateTime.now()))) {
            throw new RequestAbortedException("Not purchasable");
        }
    }

    public boolean hasEnoughStock(Long productId, int requiredStock) {
        int stock = productFeignService.getStock(productId);

        return requiredStock <= stock;
    }

    public void executePayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isPaymentUpdatable(user, payment)) {
            throw new RuntimeException("can't execute payment");
        }

        if (!tryRandomSuccess(user, payment)) {
            throw new RuntimeException("failed");
        }
    }

    @Transactional
    boolean tryRandomSuccess(User user, Payment payment) {
        // 20% 확률로 실패
        if (EventTrigger.isEventTriggered(20)) {
            payment.fail();
            paymentRepository.save(payment);

            // 재고 복구
            productFeignService.restoreStock(payment.getProductId(), payment.getAmount());

            return false;
        }

        payment.success();
        paymentRepository.save(payment);
        return true;
    }

    private boolean isPaymentUpdatable(User user, Payment payment) {
        if (payment.getStatus() != PaymentStatus.START
                || user.getId() != payment.getUserId()) {
            return false;
        }

        return true;
    }

    @Transactional
    public void cancelPayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isPaymentUpdatable(user, payment)) {
            throw new RuntimeException("can't cancel payment");
        }

        // 재고 복구
        productFeignService.restoreStock(payment.getProductId(), payment.getAmount());

        payment.cancel();
        paymentRepository.save(payment);
    }
}
