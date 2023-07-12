package com.kuxoca.mironline.service;

import com.kuxoca.mironline.entity.Currency;
import com.kuxoca.mironline.dto.CurrencyDto;
import com.kuxoca.mironline.repo.CurrencyRepo;
import com.kuxoca.mironline.util.Mapper;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;

@Log4j2
@Component
public class MainServiceImp {

    private final Mapper mapper;
    private final CurrencyRepo currencyRepo;
    private final Map<String, BigDecimal> currencyMap = new HashMap<>();

    @Value("${urlMironline}")
    private String URL;

    public MainServiceImp(Mapper mapper, CurrencyRepo currencyRepo) {
        this.mapper = mapper;
        this.currencyRepo = currencyRepo;
    }

    public void mainMethod() {

        List<Currency> currencyList = new ArrayList<>(); // буферный лист значений курсов
        Map<String, BigDecimal> currencyMapMirOnline = getCurrencyFromMirOnline(); // загрузка курсов с сайта

        currencyMapMirOnline.forEach((name, aBigDecimal) -> {
            if (currencyMap.containsKey(name)) {
                if (!(currencyMap.get(name).compareTo(aBigDecimal) == 0)) {
                    Currency currency = new Currency(name, aBigDecimal);
                    currencyList.add(currency);
                    currencyMap.put(name, aBigDecimal);
                }
            } else {
                Currency currency = new Currency(name, aBigDecimal);
                currencyList.add(currency);
                currencyMap.put(name, aBigDecimal);
            }
        });

        if (!currencyList.isEmpty()) {
            saveChangeCurrency(currencyList);
        }
    }

    @PostConstruct
    private void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        log.info("l4j. INIT APP");
        List<Currency> currencyList = new ArrayList<>();
        List<String> nameDistinctList = currencyRepo.findDistinctByName();

        if (!nameDistinctList.isEmpty()) {
            log.info("l4j. INIT FROM DB");
            nameDistinctList.forEach(name -> {
                BigDecimal aBigDecimal = currencyRepo.findLastDataByName(name.trim());
                currencyMap.put(name, aBigDecimal);
                log.info("l4j. '" + name + "' " + aBigDecimal);
            });

        } else {
            log.info("l4j. INIT FROM MirOnline");
            currencyMap.putAll(getCurrencyFromMirOnline());
            currencyMap.forEach((name, aBigDecimal) -> {
                Currency currency = new Currency(name, aBigDecimal);
                currencyList.add(currency);
            });

            if (!currencyList.isEmpty()) {
                currencyRepo.saveAll(currencyList);
            }
        }
    }

    public Set<CurrencyDto> getCurrencyDtoSet() {
        Set<CurrencyDto> set = new TreeSet<>();

        getCurrencyFromMirOnline().forEach((k, v) -> {
            CurrencyDto currencyDto = mapper.toCurrencyDto(new Currency(k, v));
            set.add(currencyDto);
        });

        return set;
    }

    private Map<String, BigDecimal> getCurrencyFromMirOnline() {

        log.info("l4j. website course check...");
        Map<String, BigDecimal> currencyMapMirOnline = new HashMap<>();

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements tables = doc.getElementsByTag("table");
            Elements tableRows = tables.get(0).getElementsByTag("tr");

            for (Element el : tableRows) {
                Elements td = el.getElementsByTag("td");
                if (!td.isEmpty()) {
                    String name = td.get(0).text().trim();
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    DecimalFormat decimalFormat = new DecimalFormat();
                    decimalFormat.setDecimalFormatSymbols(symbols);
                    decimalFormat.setParseBigDecimal(true);
                    BigDecimal curr = (BigDecimal) decimalFormat.parse(td.get(1).text().trim());
                    currencyMapMirOnline.put(name, curr);
                }
            }

        } catch (ParseException e) {
            log.error("l4j. ParseException", e);
        } catch (IOException e) {
            log.error("l4j. MirOnline is not available now", e);
        }
        return currencyMapMirOnline;
    }

    private void saveChangeCurrency(List<Currency> currencyList) {
        if (!currencyList.isEmpty()) {

            try {
                currencyRepo.saveAll(currencyList);
                currencyList.forEach(el -> log.info("l4j. Save Change currency: " + el.toString()));
            } catch (Exception e) {
                log.error("l4j. Save ERROR ", e);
                currencyList.forEach(el -> log.error("l4j. Save ERROR " + el.toString()));
            }
        }
    }
}
