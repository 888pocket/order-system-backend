package com.joosangah.userservice.auth.repository;

import com.joosangah.userservice.auth.domain.entity.BlackList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    Optional<BlackList> findByUserId(Long userId);

    @Modifying
    int deleteByUserId(Long userId);
}
