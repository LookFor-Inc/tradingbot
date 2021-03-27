package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.exceptions.UserNotFoundException;
import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.lookfor.trading.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class TradeCommandHandler implements RootCommandHandler<SendMessage> {
    private final TradeService tradeService;

    @Override
    public SendMessage doParse(Update update) {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        String[] restTextList = restOfTextMessage.split(" ");
        StringBuilder sbResponse = new StringBuilder();

        if (restTextList.length < 3) {
            sbResponse.append("Enter command */trade <HH:mm:ss> <HH:mm:ss> <ticker1, ticker2, ...>* to trade 📈");
        } else {
            try {
                String[] timeParams = Arrays.copyOfRange(restTextList, 0, 2).clone();
                List<String> tickerParams = Arrays.asList(Arrays.copyOfRange(restTextList, 2, restTextList.length).clone());
                List<Date> convertedTime = new ArrayList<>();
                for (String timeParam : timeParams) {
                    Date date = DateTimeUtil.stringToDate(timeParam, DateTimeUtil.PatternType.HH_MM_SS_COLON);
                    convertedTime.add(date);
                }
                tickerParams.forEach(ticker ->
                        tradeService.saveStartAndStopTime(
                                ticker,
                                message.getFrom().getId(),
                                convertedTime.get(0),
                                convertedTime.get(1)
                        )
                );
                sbResponse.append("Trade added 🙃🙃🙃");
            } catch (ParseException | IncorrectRequestException | UserNotFoundException | EntityNotFoundException exp) {
                log.error(exp.getMessage());
                sbResponse.append(exp.getMessage());
            }
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
