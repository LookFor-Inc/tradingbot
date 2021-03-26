package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.models.TradeDeal;
import com.lookfor.trading.services.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

import static com.lookfor.trading.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadCommandHandler implements RootCommandHandler<SendMessage> {
    private final TradeService tradeService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        StringBuilder sbResponse = new StringBuilder();

        if (!restOfTextMessage.startsWith("#")) {
            sbResponse.append("Incorrect input");
        } else {
            try {
                long tradeId = Long.parseLong(restOfTextMessage.substring(1));
                Set<TradeDeal> tradeDeals = tradeService.findAllTradeDealsByTradeId(tradeId);
                tradeDeals.forEach(tradeDeal -> sbResponse.append(tradeDeal.getId()).append(" ").append(tradeDeal.getPrice()));
            } catch (NumberFormatException | EntityNotFoundException | IncorrectRequestException exp) {
                log.error(exp.getMessage());
                sbResponse.append(exp.getMessage());
            }

        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(sbResponse.toString())
                .build();
    }
}
