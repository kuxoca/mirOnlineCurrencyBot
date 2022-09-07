package com.kuxoca.mironline.service;

import com.kuxoca.mironline.entity.TelegrammUser;
import com.kuxoca.mironline.entity.UserAction;
import com.kuxoca.mironline.repo.TelegrammUserRepo;
import com.kuxoca.mironline.repo.UserActionRepo;
import com.pengrad.telegrambot.model.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
public class LogServiceImp implements LogService {

    private final
    TelegrammUserRepo telegrammUserRepo;

    private final
    UserActionRepo userActionRepo;

    public LogServiceImp(TelegrammUserRepo telegrammUserRepo, UserActionRepo userActionRepo) {
        this.telegrammUserRepo = telegrammUserRepo;
        this.userActionRepo = userActionRepo;
    }

    public void logUserAction(Message message) {
        log.info("l4j. userId: " + message.chat().id() + ", message: '" + message.text() + "'");

        if (isNewUser(message)) {
            registrationNewUser(message);
        }
        saveAction(message);
    }

    private boolean isNewUser(Message message) {

        try {
            return telegrammUserRepo.findTelegramUserByUserId(message.chat().id()) == null;
        } catch (Exception e) {
            log.error("l4j. DB ERROR ", e);
            log.error("l4j. BD ERROR " + message);
        }
        return false;
    }

    private void registrationNewUser(Message message) {

        TelegrammUser user = new TelegrammUser();
        user.setUserId(message.chat().id());
        user.setUserName(message.chat().username());
        user.setFirsName(message.chat().firstName());
        user.setLastName(message.chat().lastName());
        user.setRegDate(LocalDateTime.now());
        log.info("l4j. REG new user " + user);

        try {
            telegrammUserRepo.save(user);
        } catch (Exception e) {
            log.error("l4j. Save ERROR ", e);
            log.error("l4j. Save ERROR " + user);
        }
    }

    private void saveAction(Message message) {
        UserAction action = new UserAction();
        try {
            TelegrammUser telegramUser = telegrammUserRepo.findTelegramUserByUserId(message.chat().id());
            action.setUser(telegramUser);
            action.setDtQuery(LocalDateTime.now());
            action.setMessage(message.text());
            userActionRepo.save(action);
        } catch (Exception e) {
            log.error("l4j. BD ERROR ", e);
            log.error("l4j. BD ERROR " + action);
        }

    }
}












































