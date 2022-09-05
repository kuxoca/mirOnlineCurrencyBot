package com.kuxoca.mironline.service;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
    void logUserAction(Message message);

}
