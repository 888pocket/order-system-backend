package com.joosangah.paymentservice.controller;

import com.joosangah.paymentservice.common.client.UserFeignClient;
import com.joosangah.paymentservice.common.domain.User;
import com.joosangah.paymentservice.domain.dto.request.PaymentRequest;
import com.joosangah.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
    public void startPayment(@RequestBody PaymentRequest request) {
        User user = userFeignClient.getUser();
        // TODO: 재고 체크

        paymentService.startPayment(user, request);
    }
}
