package com.kuxoca.mironline.service;

import com.kuxoca.mironline.dto.CurrencyDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Log4j2
@Service
public class RatesServiceImp implements RatesService {

    private final MainService mainService;
    private final MessageSource messageSource;

    public RatesServiceImp(MainService mainService, MessageSource messageSource) {
        this.mainService = mainService;
        this.messageSource = messageSource;
    }

    @Override
    public String getStringRates(Locale locale) {

        Set<CurrencyDto> set = mainService.getCurrencyDtoSet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(messageSource.getMessage("ratesService.title", null, locale))
                .append("\n");
        set.forEach(el -> {
            stringBuilder
                    .append("<b>")
                    .append(messageSource.getMessage("ratesService." + el.getAnEnum(), null, locale))
                    .append("</b>")
                    .append(": ")
                    .append(el.getABigDecimal())
                    .append("₽")
                    .append("\n");
        });
        return stringBuilder.toString();
    }
}
