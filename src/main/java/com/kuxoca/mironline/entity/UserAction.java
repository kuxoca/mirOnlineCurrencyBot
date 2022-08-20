package com.kuxoca.mironline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "mironline_useraction")
@Setter
@Getter
@NoArgsConstructor
public class UserAction extends AbstractEntity {
    @ManyToOne
    TelegrammUser user;
    LocalDateTime dtQuery;
    String message;
}
