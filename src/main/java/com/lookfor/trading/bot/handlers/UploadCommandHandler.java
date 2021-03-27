package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UploadCommandHandler implements RootCommandHandler<SendMessage> {
    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text("😎 Now you can send CSV files with period of day 😎")
                .build();
    }
}
