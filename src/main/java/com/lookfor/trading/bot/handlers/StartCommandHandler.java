package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommandHandler implements RootCommandHandler<SendMessage> {
    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(
                        String.format(
                                "Hello, %s! I am a trading bot! 🤖\n\n", message.getFrom().getUserName())
                                + "I can help you to trade 💸 WOW! 🤪")
                .build();
    }
}
