package com.joosangah.userservice.user.domain.entity;

import com.joosangah.userservice.auth.domain.enums.ERole;
import com.joosangah.userservice.common.domain.AuditEntity;
import com.joosangah.userservice.user.domain.dto.request.UserForm;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;
    private String password;
    @Column(name = "profile_image")
    private String profileImage;
    private String introduction;
    @Column(name = "remember_token")
    private String rememberToken;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @Builder
    public User(String name, String email, LocalDateTime emailVerifiedAt, String password,
            String profileImage, String introduction, String rememberToken, ERole role) {
        this.name = name;
        this.email = email;
        this.emailVerifiedAt = emailVerifiedAt;
        this.password = password;
        this.profileImage = profileImage;
        this.introduction = introduction;
        this.rememberToken = rememberToken;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    public User modify(UserForm request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }
        this.introduction = request.getIntroduction();

        return this;
    }

    public User modifyProfileImage(String url) {
        this.profileImage = url;

        return this;
    }

    public User modifyPassword(String password) {
        this.password = password;

        return this;
    }

    public void verifyEmail() {
        this.emailVerifiedAt = LocalDateTime.now();
    }
}
