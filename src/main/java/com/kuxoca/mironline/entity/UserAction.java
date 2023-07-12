package com.kuxoca.mironline.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mironline_useraction")
public class UserAction extends AbstractEntity {
    @ManyToOne
    TelegramUser user;
    LocalDateTime dtQuery;
    String message;
}
