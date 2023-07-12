package com.kuxoca.mironline.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mironline_telegramuser")
public class TelegramUser extends AbstractEntity {
    Long userId;
    String firsName;
    String lastName;
    String userName;
    LocalDateTime regDate;
}
