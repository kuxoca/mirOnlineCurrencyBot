package com.kuxoca.mironline.util;

import com.kuxoca.mironline.dto.CodeEnum;
import com.kuxoca.mironline.entity.Currency;
import com.kuxoca.mironline.dto.CurrencyDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Mapper {
    public CurrencyDto toCurrencyDto(Currency currency) {
        return new CurrencyDto(Arrays.stream(CodeEnum.values()).filter(el -> el.getValue().equals(currency.getName())).findAny().orElse(null), currency.getCurrency());
    }

    public Currency toCurrency(CurrencyDto currencyDto) {
        return new Currency(currencyDto.getAnEnum().getValue(), currencyDto.getAFloat());
    }
}
