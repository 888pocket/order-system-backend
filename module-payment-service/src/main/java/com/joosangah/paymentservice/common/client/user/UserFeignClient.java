package com.joosangah.paymentservice.common.client.user;

import com.joosangah.paymentservice.common.client.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping("/user/internal")
    User getUser();
}
