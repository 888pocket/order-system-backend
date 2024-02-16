package com.joosangah.paymentservice.common.client;

import com.joosangah.paymentservice.common.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UserFeignClient", url = "http://user-service:8080/user", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping("/internal")
    User getUser();
}
