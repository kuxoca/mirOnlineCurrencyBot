package com.kuxoca.mironline.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RatesServiceImp implements RatesService {

    private final
    MainService mainService;

    private static final Logger logger = Logger.getLogger(RatesServiceImp.class);

    public RatesServiceImp(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    public String getStringRates() {

        Map<String, Float> currencyMap = mainService.getCurrencyMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Курсы валют платежной системы «Мир»:</b>\n");

        currencyMap.forEach((name, currency) -> {
            stringBuilder
                    .append("<b>").append(name).append("</b>").append(": ")
                    .append(currency).append("₽").append("\n");
        });

        stringBuilder.append("// Отражено как количество RUB за 1 единицу иностранной валюты.");
        return stringBuilder.toString();
    }
}
