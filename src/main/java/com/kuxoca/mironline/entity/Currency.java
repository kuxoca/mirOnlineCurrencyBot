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
@Table(name = "mironline_currency")
public class Currency extends AbstractEntity {
    LocalDateTime localDateTime;
    String name;
    Float currency;

    public Currency(String name, Float currency) {
        this.name = name;
        this.currency = currency;
        this.localDateTime = LocalDateTime.now();
    }

}
