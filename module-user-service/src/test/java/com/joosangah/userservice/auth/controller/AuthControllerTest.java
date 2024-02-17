package com.joosangah.userservice.auth.controller;

import com.joosangah.userservice.auth.security.payload.request.LoginRequest;
import com.joosangah.userservice.auth.security.payload.response.JwtResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Test
    void 회원_데이터_준비() throws IOException {
        FileWriter fileWriter = new FileWriter("token_list.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < 10000; i++) {
            ResponseEntity response = authController.authenticateUser(LoginRequest.builder()
                    .password("password")
                    .email("test" + i + "@test.com").build());

            JwtResponse jwt = (JwtResponse) response.getBody();
            bufferedWriter.write(jwt.getToken() + "\n");
        }

        bufferedWriter.flush();
        bufferedWriter.close();
    }
}