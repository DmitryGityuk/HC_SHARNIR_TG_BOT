package com.example.HC_SHARNIR_BOT.service.impl;

import com.example.HC_SHARNIR_BOT.model.User;
import com.example.HC_SHARNIR_BOT.repository.UserRepository;
import com.example.HC_SHARNIR_BOT.service.UserService;
import com.example.HC_SHARNIR_BOT.utils.Emojis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final MessageServiceImpl messageService;
    private final UserRepository userRepository;

    public UserServiceImpl(MessageServiceImpl messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isPresent()) {
            msg.setText("Второй раз то тебе нахуя?");
            messageService.sendMessage(msg.getChatId(), msg.getText());
        } else if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();
            User user = new User();
            user.setChatId(chatId);
            user.setFullName(chat.getFirstName() + " " + chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            user.setGoals(1);
            user.setAssists(1);
            user.setComb(user.getGoals() + user.getAssists());
            user.setCountVisits(1);
            user.setCountGames(1);
            user.setAverageScore((float) user.getComb() / user.getCountGames());
            userRepository.save(user);
            msg.setText("Отлично, добавил тебя в список участников зимнего сезона, удачи чемпион " + Emojis.MUSCLE);
            messageService.sendMessage(msg.getChatId(), msg.getText());
            log.info("User saved " + user);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public String showTop3Assists(Message msg) {
        ArrayList<User> players = (ArrayList<User>) userRepository.getUserByAssists();
        return getString(players);
    }

    @Transactional(readOnly = true)
    @Override
    public String infoAboutMe(Message msg) {
        return String.valueOf((userRepository.findById(msg.getChatId()).get()));
    }

    @Transactional(readOnly = true)
    @Override
    public String showTop3Players() {
        ArrayList<User> players = (ArrayList<User>) userRepository.getUserByComb();
        return getString(players);
    }

    @Transactional(readOnly = true)
    @Override
    public String showTop3Scorers(Message msg) {
        ArrayList<User> players = (ArrayList<User>) userRepository.getUserByGoals();
        return getString(players);
    }

    @Override
    public String showAllPlayers(Message msg) {
        String pathToTable = "https://docs.google.com/spreadsheets/d/1o3kOJpb_yMz-_hngTE7LphrGz7GAy0dzHCQ3OCOu3Xw/edit#gid=0";
        return pathToTable;
    }

    private String getString(ArrayList<User> players) {
        return IntStream.range(0, players.size())
                .mapToObj(index -> {
                    User pl = players.get(index);
                    index++;
                    return String.format(index + " место: " + "имя - %s, игр: %s, пасы: %s, голы: %s, гол + пас: %s, среднее: %s. \n",
                            pl.getFullName(), pl.getCountGames(), pl.getAssists(), pl.getGoals(), pl.getComb(), pl.getAverageScore());
                })
                .collect(Collectors.joining());
    }
}
