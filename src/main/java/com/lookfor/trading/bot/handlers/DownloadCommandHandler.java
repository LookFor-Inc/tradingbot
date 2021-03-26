package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.services.UserTickerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadCommandHandler implements RootCommandHandler<SendMessage> {
    private final UserTickerService userTickerService;
    private final TradeService tradeService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        StringBuilder sbResponse = new StringBuilder();

        List<UserTicker> userTickers = userTickerService.findAllByUserId(message.getFrom().getId());
        if (userTickers.isEmpty()) {
            sbResponse.append("You do not have any available tickers");
        } else {
            for (UserTicker userTicker : userTickers) {
                List<Trade> trades = tradeService.findAllByUserTickerId(userTicker.getId());
                sbResponse.append("\nFor ticker - ");
                if (trades.isEmpty()) {
                    sbResponse
                            .append(userTicker.getName())
                            .append(" you didn't have any available trades");
                } else {
                    sbResponse
                            .append(userTicker.getName())
                            .append(", you can download csv file by executing command: /download #1\n");
                    trades.forEach(trade -> sbResponse
                            .append("Yours available trades:\n")
                            .append(trade.getStart())
                            .append(" ")
                            .append(trade.getStop())
                            .append(" #")
                            .append(trade.getId())
                            .append("\n"));
                }
            }
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(sbResponse.toString())
                .build();
    }
}
