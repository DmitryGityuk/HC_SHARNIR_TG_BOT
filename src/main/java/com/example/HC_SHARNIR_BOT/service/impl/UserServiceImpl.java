package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.model.User;
import com.example.HC_SHARNIR_BOT.repository.UserRepository;
import com.example.HC_SHARNIR_BOT.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();
            User user = new User();
            user.setChatId(chatId);
            user.setFullName(chat.getFirstName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            user.setGoals(0);
            user.setAssists(0);
            user.setComb(0);
            userRepository.save(user);
            log.info("User saved " + user);
        }
    }
}
