package com.example.HC_SHARNIR_BOT.service;

import com.example.HC_SHARNIR_BOT.config.BotConfig;
import com.example.HC_SHARNIR_BOT.service.impl.MessageServiceImpl;
import com.example.HC_SHARNIR_BOT.service.impl.NotificationsServiceImpl;
import com.example.HC_SHARNIR_BOT.service.impl.RegisterServiceImpl;
import com.example.HC_SHARNIR_BOT.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@EnableScheduling
public class TelegramBot extends TelegramLongPollingBot {
    private final UserServiceImpl userServiceimpl;
    private final MainMenu mainMenu;
    private final MessageServiceImpl messageService;
    private final RegisterServiceImpl registerService;
    private final NotificationsServiceImpl notifications;
    private final BotConfig config;

    public TelegramBot(BotConfig config, UserServiceImpl service, MainMenu mainMenu, MessageServiceImpl messageService,
                       RegisterServiceImpl registerService, NotificationsServiceImpl notifications) {
        this.userServiceimpl = service;
        this.config = config;
        this.mainMenu = mainMenu;
        this.messageService = messageService;
        this.registerService = registerService;
        this.notifications = notifications;
        try {
            this.execute(new SetMyCommands(mainMenu.getCommandList(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException ex) {
            log.error("Error setting bot's command list: " + ex.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (message) {
                case "/start" -> messageService.sendWelcomeMessage(chatId, update.getMessage().getChat().getFirstName());
                case "/help", "Помощь" -> messageService.sendHelpMessage(chatId, update.getMessage().getChat().getFirstName());
                case "/mydata", "Про меня" -> messageService.sendMessage(chatId, userServiceimpl.infoAboutMe(update.getMessage()));
                case "/topassists", "Голевые пасы" -> messageService.sendMessage(chatId, String.valueOf(userServiceimpl.showTop3Assists(update.getMessage())));
                case "/topscorers", "Голы" -> messageService.sendMessage(chatId, String.valueOf(userServiceimpl.showTop3Scorers(update.getMessage())));
                case "/topcomb", "Голы + Пасы" -> messageService.sendMessage(chatId, String.valueOf(userServiceimpl.showTop3Players()));
                case "/allplayers", "Таблица" -> messageService.sendMessage(chatId, String.valueOf(userServiceimpl.showAllPlayers(update.getMessage())));
                case "Регистрация" -> userServiceimpl.registerUser(update.getMessage());
                case "Понедельник" -> messageService.sendMessageRegisterOnMondayGame();
                case "Четверг" -> messageService.sendMessageRegisterOnThursdayGame();
                case "Список" -> registerService.sendListPlayers();
                case "Деньги" -> notifications.sendNotification();
            }
        } else if (update.hasCallbackQuery()) {
            registerService.registerPlayerOnGame(update);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}