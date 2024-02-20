package com.joosangah.paymentservice.common.client.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private String introduction;
}
