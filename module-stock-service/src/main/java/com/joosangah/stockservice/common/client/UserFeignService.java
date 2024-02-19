package com.joosangah.stockservice.common.client;

import com.joosangah.stockservice.common.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignService {

    private final UserFeignClient userFeignClient;

    public User getUser() {
        return userFeignClient.getUser();
    }
}
