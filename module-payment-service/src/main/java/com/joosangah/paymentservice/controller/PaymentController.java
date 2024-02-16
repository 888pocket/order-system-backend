package com.joosangah.paymentservice.controller;

import com.joosangah.paymentservice.common.client.UserFeignClient;
import com.joosangah.paymentservice.common.domain.User;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final UserFeignClient userFeignClient;

    private final PaymentService paymentService;

    @PostMapping("/start")
    public void startPayment(@RequestBody PaymentRequest request) throws RequestAbortedException {
        User user = userFeignClient.getUser();
        // 재고, 오픈 일정 체크
        paymentService.validateProductForPurchase(request.getProductId(), request.getAmount());

        paymentService.startPayment(user, request);
    }

    @PutMapping("/{paymentId}/execute")
    public void executePayment(@PathVariable Long paymentId) {
        User user = userFeignClient.getUser();
        paymentService.executePayment(user, paymentId);
    }

    @PutMapping("/{paymentId}/cancel")
    public void cancelPayment(@PathVariable Long paymentId) {
        User user = userFeignClient.getUser();
        paymentService.cancelPayment(user, paymentId);
    }
}
