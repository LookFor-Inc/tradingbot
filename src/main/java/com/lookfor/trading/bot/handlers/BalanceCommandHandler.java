package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.services.UserTickerService;
import com.lookfor.trading.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.lookfor.trading.utils.DateTimeUtil.dateToString;

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
        if (userTickers.isEmpty()) {
            sbResponse.append("😔 You do not have any available tickers 😔");
        } else {
            userTickers.forEach(ticker -> {
                sbResponse
                        .append("📈 ")
                        .append(ticker.getName())
                        .append("\n");
                List<Trade> trades = tradeService.findAllByUserTickerId(ticker.getId());
                if (trades.isEmpty()) {
                    sbResponse.append("You do not have any trades for this ticker\n");
                } else {
                    trades.forEach(trade -> sbResponse
                            .append(dateToString(trade.getStart(), DateTimeUtil.PatternType.HH_MM_SS_COLON))
                            .append("-")
                            .append(dateToString(trade.getStop(), DateTimeUtil.PatternType.HH_MM_SS_COLON))
                            .append(" ")
                            .append(trade.getBalance())
                            .append("\n"));
                }
            });
        }
        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
