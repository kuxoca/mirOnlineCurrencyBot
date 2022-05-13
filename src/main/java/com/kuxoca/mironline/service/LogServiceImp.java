package com.kuxoca.mironline.service;

import com.pengrad.telegrambot.model.Message;
import com.kuxoca.mironline.entity.TelegrammUser;
import com.kuxoca.mironline.entity.UserAction;
import com.kuxoca.mironline.repo.TelegrammUserRepo;
import com.kuxoca.mironline.repo.UserActionRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImp implements LogService {
    @Autowired
    TelegrammUserRepo telegrammUserRepo;

    @Autowired
    UserActionRepo userActionRepo;

    public void logUserAction(Message message) {
        System.out.println("----LOG-SERVICE----" + LocalDateTime.now());
        System.out.println("userId: " + message.chat().id() + ", " + message.text());
        registrationUser(message);
        saveAction(message);
    }

    private void registrationUser(Message message) {

        if (telegrammUserRepo.findTelegramUserByUserId(message.chat().id()) == null) {
            TelegrammUser user = new TelegrammUser();
            user.setUserId(message.chat().id());
            user.setUserName(message.chat().username());
            user.setFirsName(message.chat().firstName());
            user.setLastName(message.chat().lastName());
            user.setRegDate(LocalDateTime.now());
            telegrammUserRepo.save(user);
            System.out.println("REG new user " + user);
        }
    }

    private void saveAction(@NotNull Message message) {
        UserAction action = new UserAction();
        action.setUserId(message.chat().id());
        action.setDtQuery(LocalDateTime.now());
        action.setMessage(message.text());
        userActionRepo.save(action);
    }
}












































