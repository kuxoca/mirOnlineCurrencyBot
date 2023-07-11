package com.kuxoca.mironline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    @Column(precision = 16, scale = 10)
    BigDecimal currency;

    public Currency(String name, BigDecimal currency) {
        this.name = name;
        this.currency = currency;
        this.localDateTime = LocalDateTime.now();
    }

}
