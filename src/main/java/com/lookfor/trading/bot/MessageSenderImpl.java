package com.lookfor.trading.bot;

import com.lookfor.trading.config.TelegramBot;
import com.lookfor.trading.interfaces.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {
    private final TelegramBot telegramBot;

    @Override
    public void sendToUser(Integer userId, String sourceText) throws TelegramApiException {
        telegramBot.execute(
                SendMessage.builder()
                .chatId(String.valueOf(userId))
                .parseMode(ParseMode.MARKDOWN)
                .text(sourceText)
                .build()
        );
    }

    @Override
    public void sendToUsers(Collection<Integer> usersId, String sourceText) throws TelegramApiException {
        for (Integer userId : usersId) {
            sendToUser(userId, sourceText);
        }
    }
}
