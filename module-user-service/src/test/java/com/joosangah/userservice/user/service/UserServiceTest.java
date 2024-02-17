package com.joosangah.userservice.user.service;

import com.joosangah.userservice.user.domain.dto.request.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void 회원_데이터_준비() {
        for (int i = 0; i < 10000; i++) {
            userService.addUser(SignupRequest.builder()
                    .name("test name " + i)
                    .password("password")
                    .email("test" + i + "@test.com").build());
        }
    }
}