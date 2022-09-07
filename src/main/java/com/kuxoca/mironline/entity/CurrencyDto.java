package com.kuxoca.mironline.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyDto implements Comparable<CurrencyDto> {
    CodeEnum anEnum;
    Float aFloat;

    @Override
    public int compareTo(@NotNull CurrencyDto o) {
        return this.getAnEnum().compareTo(o.getAnEnum());
    }
}



































