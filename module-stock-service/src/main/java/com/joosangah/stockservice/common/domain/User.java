package com.joosangah.stockservice.common.domain;

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
