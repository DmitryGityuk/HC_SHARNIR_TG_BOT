package com.example.HC_SHARNIR_BOT.service;

import org.telegram.telegrambots.meta.api.objects.Message;


public interface UserService {

    void registerUser(Message msg);

    String showTop3Assists(Message msg);

    String infoAboutMe(Message msg);

    String showTop3Players();

    String showTop3Scorers(Message msg);

    String showAllPlayers(Message msg);
}

