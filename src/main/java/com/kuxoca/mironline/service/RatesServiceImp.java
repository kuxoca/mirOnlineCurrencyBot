package com.kuxoca.mironline.service;

import com.kuxoca.mironline.entity.CodeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

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

        Map<CodeEnum, Float> currencyMapByCode;
        currencyMapByCode = mainService.getCurrencyMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageSource.getMessage("ratesService.title", null, locale));
        currencyMapByCode.forEach((charCode, currency) -> {
            stringBuilder
                    .append("<b>")
                    .append(messageSource.getMessage("ratesService." + charCode, null, locale))
                    .append("</b>")
                    .append(": ")
                    .append(currency)
                    .append("₽")
                    .append("\n");
        });
        return stringBuilder.toString();
    }
}
