package com.kuxoca.mironline.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "mironline_currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime localDateTime;
    String name;
    float currency;

    public Currency(String name, Float currency) {
        this.name = name;
        this.currency = currency;
        this.localDateTime = LocalDateTime.now();
    }

}
