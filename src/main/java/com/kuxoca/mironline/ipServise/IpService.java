package com.kuxoca.mironline.ipServise;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IpService {


    private static final Logger logger = Logger.getLogger(IpService.class);

    private static final String ipRegex = "(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}";
    private String ip = "";

    private static List<URL> urls;

    static {
        try {
            urls = Arrays.asList(
                    new URL("http://checkip.amazonaws.com/"),
                    new URL("https://myexternalip.com/raw"),
                    new URL("https://ipv4.icanhazip.com/"),
                    new URL("http://ipecho.net/plain"),
                    new URL("http://icanhazip.com/"),
                    new URL("http://myip.dnsomatic.com/")
            );
        } catch (MalformedURLException e) {
            logger.error("l4j IP", e);
        }
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000L)  // каждые 5 минуты обноавляем IP
    public void scheduled1() {
        makeIpAddress();
//        dehssisfsFeignClient.setIpOnDehssisfs(ip); // каждую минуту отправляем запрос на heroku
    }

    @PostConstruct
    private void makeIpAddress() {

        for (URL el : urls) {
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(el.openStream())
                );
                String ip = in.readLine();
                if (isIp(ip)) {
                    logger.info("l4j. Load IP from: " + el + ", " + ip);
                    saveIp(ip);
                    break;
                }
            } catch (Exception e) {
                logger.error("l4j IP, '" + el + "' ", e);

            }
        }
    }

    private boolean isIp(String ip) { // проверка является ли строка IP адресом
        Pattern p = Pattern.compile(ipRegex);
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    public boolean saveIp(String ip) {

        if (isIp(ip)) {
            if (!this.ip.equals(ip)) {
                logger.info("l4j. new IP: " + ip);
                this.ip = ip;
//                ipRepo.save(new IpEntity(ip));
            }
            return true;
        } else {
            logger.info("l4j. IP not found");
            return false;
        }
    }

    public String getIp() {
        return ip;
    }


//    https://ru.stackoverflow.com/questions/579709/%D0%9E%D0%B1%D1%8B%D1%87%D0%BD%D1%8B%D0%B9-get-%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81-%D0%BD%D0%B0-java

}