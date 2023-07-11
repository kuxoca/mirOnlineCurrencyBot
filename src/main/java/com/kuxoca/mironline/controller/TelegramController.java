package com.kuxoca.mironline.controller;

import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.kuxoca.mironline.service.LogService;
import com.kuxoca.mironline.service.RatesService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import java.util.Locale;

@BotController
public class TelegramController implements TelegramMvcController {
    final MessageSource messageSource;
    final RatesService ratesService;
    private final LogService logService;
    @Value("${telegramBotToken}")
    private String token;

    public TelegramController(LogService logService, RatesService ratesService, MessageSource messageSource) {
        this.logService = logService;
        this.ratesService = ratesService;
        this.messageSource = messageSource;
    }

    @BotRequest(value = "/start", type = {MessageType.MESSAGE})
    public SendMessage start(Message message) {
        Long id = message.chat().id();
        logService.logUserAction(message);
        return new SendMessage(
                message.chat().id(),
                messageSource.getMessage("telegramController.start", new String[]{message.chat().firstName()}, getLocaleFromMessage(message)))
                .parseMode(ParseMode.HTML);
    }

    @BotRequest(type = {MessageType.ANY})
    public SendMessage anyMessage(Message message) {
        logService.logUserAction(message);
        return new SendMessage(
                message.chat().id(),
                messageSource.getMessage("telegramController.anyMessage", null, getLocaleFromMessage(message)))
                .parseMode(ParseMode.HTML);
    }

    @BotRequest(value = "/rates", type = {MessageType.MESSAGE})
    public SendMessage rates(Message message) {
        logService.logUserAction(message);
        return new SendMessage(message.chat().id(), ratesService.getStringRates(getLocaleFromMessage(message))).parseMode(ParseMode.HTML);
    }

    private Locale getLocaleFromMessage(Message message) {
        return new Locale(message.from().languageCode());
    }

    @Override
    public String getToken() {
        return token;
    }
}













































