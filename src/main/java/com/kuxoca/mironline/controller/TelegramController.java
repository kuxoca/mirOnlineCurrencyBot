package com.kuxoca.mironline.controller;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.kuxoca.mironline.service.LogService;
import com.kuxoca.mironline.service.MainService;
import com.kuxoca.mironline.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@BotController
public class TelegramController implements TelegramMvcController {
    @Value("${telegramBotToken}")
    private String token;

    @Override
    public String getToken() {
        return token;
    }

    @Autowired
    LogService logService;

    @Autowired
    MainService mainService;

    @BotRequest(value = "/start", type = {MessageType.MESSAGE})
    public SendMessage start(Message message) {
        Long id = message.chat().id();
        logService.logUserAction(message);
        return new SendMessage(message.chat().id(),
                "Привет!\n" +
                        "Это неофициальный сервис (бот). Автор не несет какой либо ответсвености.\n" +
                        "Указанные значения курсов являются справочным.\n" +
                        "Узнать текущие курсы можно на официальном сайте ПС \"МИР\": https://mironline.ru/\n\n" +
                        "Показать текущий курс /rates").parseMode(ParseMode.HTML);
    }

    @BotRequest(type = {MessageType.ANY})
    public SendMessage anyMessage(Message message) {
        logService.logUserAction(message);
        return new SendMessage(message.chat().id(), "ох... я пока не понимаю эту команду.\n" +
                "Показать текущий курс /rates");
    }

    @Autowired
    RatesService ratesService;

    @BotRequest(value = "/rates", type = {MessageType.MESSAGE})
    public SendMessage rates(Message message) {
        logService.logUserAction(message);
        return new SendMessage(message.chat().id(), ratesService.getStringRates()).parseMode(ParseMode.HTML);
    }

}













































