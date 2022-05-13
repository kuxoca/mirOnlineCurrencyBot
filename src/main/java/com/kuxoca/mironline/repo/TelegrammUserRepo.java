package com.kuxoca.mironline.repo;

import com.kuxoca.mironline.entity.TelegrammUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelegrammUserRepo extends JpaRepository<TelegrammUser, Long> {
    @Query(value = "select u from TelegrammUser u where u.userId = :userId")
    TelegrammUser findTelegramUserByUserId(@Param("userId") Long userId);
}
