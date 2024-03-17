package com.joosangah.stockservice.common.client;

import com.joosangah.stockservice.common.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping("/user/internal")
    User getUser();
}
