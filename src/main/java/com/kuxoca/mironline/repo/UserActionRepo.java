package com.kuxoca.mironline.repo;

import com.kuxoca.mironline.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionRepo extends JpaRepository<UserAction, Long> {
}
