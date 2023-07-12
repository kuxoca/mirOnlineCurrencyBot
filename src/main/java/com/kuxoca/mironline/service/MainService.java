package com.kuxoca.mironline.service;

import com.kuxoca.mironline.dto.CurrencyDto;

import java.util.Set;

public interface MainService {
    Set<CurrencyDto> getCurrencyDtoSet();

    void updateCurrency();
}
