package com.kuxoca.mironline.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyDto implements Comparable<CurrencyDto> {
    CodeEnum anEnum;
    BigDecimal aFloat;

    @Override
    public int compareTo(@NotNull CurrencyDto o) {
        return this.getAnEnum().compareTo(o.getAnEnum());
    }
}



































