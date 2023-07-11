package com.kuxoca.mironline.repo;

import com.kuxoca.mironline.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelegramUserRepo extends JpaRepository<TelegramUser, Long> {
    @Query(value = "select u from TelegramUser u where u.userId = :userId")
    TelegramUser findTelegramUserByUserId(@Param("userId") Long userId);
}
