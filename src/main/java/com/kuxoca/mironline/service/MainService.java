package com.kuxoca.mironline.service;

import com.kuxoca.mironline.entity.Currency;
import com.kuxoca.mironline.repo.CurrencyRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class MainService {

    @Value("${URL_MIRONLINE}")
    private String URL;

    @Autowired
    private CurrencyRepo currencyRepo;
    private final Map<String, Float> currencyMap = new HashMap<>();

    public Map<String, Float> getCurrencyMap() {
        return currencyMap;
    }

    public void mainMethod() throws IOException, ParseException {
        System.out.println("----mainMethod---- " + LocalDateTime.now());

        List<Currency> currencyList = new ArrayList<>();
        Map<String, Float> currencyMapMironline = getCurrencyFromMironline();

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
            System.out.println("Change currency:");
            currencyList.forEach(System.out::println);
        }
    }

    @PostConstruct
    private void init() throws IOException, ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        System.out.println("----INIT----");
        List<String> nameDistinctList = currencyRepo.findDistinctByName();

        if (!nameDistinctList.isEmpty()) {
            nameDistinctList.forEach(name -> {
                List<Float> aFloat = currencyRepo.findLastDataByName(name);
                currencyMap.put(name, aFloat.get(0));
            });
            System.out.println("----INIT FROM DB----");
        } else {
            currencyMap.putAll(getCurrencyFromMironline());

            List<Currency> currencyList = new ArrayList<>();

            currencyMap.forEach((name, aFloat) -> {
                Currency currency = new Currency(name, aFloat);
                currencyList.add(currency);
                System.out.println(currency);
            });
            if (!currencyList.isEmpty()) {
                currencyRepo.saveAll(currencyList);
            }
            System.out.println("----INIT FROM Mironline----");
        }
    }

    private Map<String, Float> getCurrencyFromMironline() throws IOException, ParseException {

        Map<String, Float> currencyMapMironline = new HashMap<>();

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
        return currencyMapMironline;
    }

    private void saveChangeCurrency(List<Currency> currencyList) {
        if (!currencyList.isEmpty()) {
            currencyRepo.saveAll(currencyList);
            currencyList.forEach(System.out::println);
        }
    }
}
