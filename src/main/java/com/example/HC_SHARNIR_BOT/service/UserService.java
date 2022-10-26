package com.example.HC_SHARNIR_BOT.service;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;


public interface UserService {

    void registerUser(Message msg);

    List<String> showTop3Assists(Message msg);

    String infoAboutMe(Message msg);

    List<String> showTop3Players(Message msg);

    List<String> showTop3Scorers(Message msg);
}

