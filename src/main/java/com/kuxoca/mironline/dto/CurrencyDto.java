package com.kuxoca.mironline.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyDto implements Comparable<CurrencyDto> {
    CodeEnum anEnum;
    BigDecimal aBigDecimal;

    @Override
    public int compareTo(@NotNull CurrencyDto o) {
        return this.getAnEnum().compareTo(o.getAnEnum());
    }
}



































