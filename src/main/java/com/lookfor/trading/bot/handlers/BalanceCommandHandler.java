package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.services.TradeService;
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
public class BalanceCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserTickerService userTickerService;
    private final TradeService tradeService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        StringBuilder sbResponse = new StringBuilder();

        List<UserTicker> userTickers = userTickerService.findAllByUserId(message.getFrom().getId());
        if (!userTickers.isEmpty()) {
            userTickers.forEach(ticker -> {
                sbResponse.append(ticker.getName())
                        .append("\n");
                tradeService.findAllByUserTickerId(ticker.getId())
                        .forEach(trade -> sbResponse.append(trade.getStart())
                                .append(" ")
                                .append(trade.getStop())
                                .append(" ")
                                .append(trade.getBalance())
                                .append("\n"));
            });
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
