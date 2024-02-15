package com.joosangah.userservice.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.joosangah.userservice.user.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 테스트() {
        User user = User.builder()
                .name("test")
                .password("pwd").build();

        userRepository.save(user);
    }
}