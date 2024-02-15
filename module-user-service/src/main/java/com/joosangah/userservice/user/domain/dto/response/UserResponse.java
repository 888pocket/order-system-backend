package com.joosangah.userservice.user.domain.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String name;
    private String email;
    private String profileImage;
    private String introduction;
    private LocalDateTime createdAt;

    public UserResponse(String name, String email, String profileImage, String introduction,
            LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.createdAt = createdAt;
    }
}
