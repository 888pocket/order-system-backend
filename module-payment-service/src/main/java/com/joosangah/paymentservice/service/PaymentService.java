package com.joosangah.paymentservice.service;

import com.joosangah.paymentservice.common.domain.User;
import com.joosangah.paymentservice.common.util.EventTrigger;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.domain.entity.Payment;
import com.joosangah.paymentservice.domain.enums.PaymentStatus;
import com.joosangah.paymentservice.repository.PaymentRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

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

        paymentRepository.save(payment);
    }

    public void executePayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isUpdatable(user, payment)) {
            throw new RuntimeException("can't execute payment");
        }

        if(!tryRandomSuccess(user, payment)) {
            throw new RuntimeException("failed");
        }
    }

    @Transactional
    boolean tryRandomSuccess(User user, Payment payment) {
        // 20% 확률로 실패
        if (EventTrigger.isEventTriggered(20)) {
            payment.fail();
            paymentRepository.save(payment);

            // TODO: 재고 복구

            return false;
        }

        payment.success();
        paymentRepository.save(payment);
        return true;
    }

    private boolean isUpdatable(User user, Payment payment) {
        if (payment.getStatus() != PaymentStatus.START
                || user.getId() != payment.getUserId()) {
            return false;
        }

        return true;
    }

    @Transactional
    public void cancelPayment(User user, Long paymentId) {
        Payment payment = loadPayment(paymentId);

        if (!isUpdatable(user, payment)) {
            throw new RuntimeException("can't cancel payment");
        }

        // TODO: 재고 복구

        payment.cancel();
        paymentRepository.save(payment);
    }
}
