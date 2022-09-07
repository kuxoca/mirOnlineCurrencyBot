package com.kuxoca.mironline.service;

import com.kuxoca.mironline.entity.CodeEnum;
import com.kuxoca.mironline.entity.Currency;
import com.kuxoca.mironline.repo.CurrencyRepo;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;

@Log4j2
@Component
public class MainService {

    private final CurrencyRepo currencyRepo;
    private final Map<String, Float> currencyMap = new HashMap<>();
    @Value("${urlMironline}")
    private String URL;

    public MainService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    public void mainMethod() {

        List<Currency> currencyList = new ArrayList<>(); // буферный лист значений курсов
        Map<String, Float> currencyMapMironline = getCurrencyFromMironline(); // загрузка курсов с сайта

        Currency c = new Currency();
        currencyMapMironline.forEach((name, aFloat) -> {
            if (currencyMap.containsKey(name)) {
                if (!(currencyMap.get(name).equals(aFloat))) {
                    Currency currency = new Currency(name, aFloat);
                    currencyList.add(currency);
                    currencyMap.put(name, aFloat);
                }
            } else {
                Currency currency = new Currency(name, aFloat);
                currencyList.add(currency);
                currencyMap.put(name, aFloat);
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
                List<Float> aFloat = currencyRepo.findLastDataByName(name.trim());
                currencyMap.put(name, aFloat.get(0));
                log.info("l4j. '" + name + "' " + aFloat.get(0));
            });

        } else {
            log.info("l4j. INIT FROM Mironline");
            currencyMap.putAll(getCurrencyFromMironline());
            currencyMap.forEach((name, aFloat) -> {
                Currency currency = new Currency(name, aFloat);
                currencyList.add(currency);
            });

            if (!currencyList.isEmpty()) {
                currencyRepo.saveAll(currencyList);
            }
        }
    }

    public Map<CodeEnum, Float> getCurrencyMap() {
        Map<CodeEnum, Float> map = new HashMap<>();

        currencyMap.forEach((k, v) -> {
            CodeEnum anEnum = Arrays.stream(CodeEnum.values()).filter(code -> code.getValue().equals(k)).findAny().orElse(null);
            map.put(anEnum, v);
        });

        return map;
    }

    private Map<String, Float> getCurrencyFromMironline() {

        log.info("l4j. website course check...");
        Map<String, Float> currencyMapMironline = new HashMap<>();

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
                    Float curr = decimalFormat.parse(td.get(1).text().trim()).floatValue();
                    currencyMapMironline.put(name, curr);
                }
            }

        } catch (ParseException e) {
            log.error("l4j. ParseException", e);
        } catch (IOException e) {
            log.error("l4j. MirOnline is not available now", e);
        }
        return currencyMapMironline;
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
