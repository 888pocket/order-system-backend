package com.joosangah.userservice.auth.domain.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Table(name = "blacklist")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Instant expiryDate;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public BlackList(Long userId, Instant expiryDate) {
        this.userId = userId;
        this.expiryDate = expiryDate;
    }
}
