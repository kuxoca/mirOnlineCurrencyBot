package com.kuxoca.mironline.service;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public interface RatesService {
    String getStringRates(Locale locale);
}
