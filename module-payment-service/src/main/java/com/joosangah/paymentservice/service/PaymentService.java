package com.joosangah.paymentservice.service;

import com.joosangah.paymentservice.common.domain.User;
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
                .amount(request.getAmount())
                .status(PaymentStatus.START).build();

        paymentRepository.save(payment);
    }
}
