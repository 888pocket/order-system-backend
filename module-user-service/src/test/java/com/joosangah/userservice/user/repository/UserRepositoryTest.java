package com.joosangah.userservice.user.repository;

import com.joosangah.userservice.user.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 데이터_삽입_테스트() {
        User user = User.builder()
                .name("test")
                .password("pwd").build();

        userRepository.save(user);
    }
}