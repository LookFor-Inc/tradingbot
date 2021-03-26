package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.services.UserTickerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ToolsCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserTickerService userTickerService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        StringBuilder sbResponse = new StringBuilder();

        List<String> userTickerNames = userTickerService.findAllUserTickerNames(message.getFrom().getId());

        if (userTickerNames.isEmpty()) {
            sbResponse.append("You do not have any available tickers");
        } else {
            sbResponse.append("You can start trading using these tickers😇:\n");
            userTickerNames.forEach(name -> sbResponse
                    .append("- ")
                    .append(name)
                    .append('\n'));
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
