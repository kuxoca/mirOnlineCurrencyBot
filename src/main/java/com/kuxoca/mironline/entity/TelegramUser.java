package com.kuxoca.mironline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "mironline_telegramuser")
public class TelegramUser extends AbstractEntity {
    Long userId;
    String firsName;
    String lastName;
    String userName;
    LocalDateTime regDate;
}
