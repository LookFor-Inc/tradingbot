package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.exceptions.UserNotFoundException;
import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.utils.TimeUtil;
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
            sbResponse.append("Incorrect enter!");
        } else {
            try {
                String[] timeParams = Arrays.copyOfRange(restTextList, 0, 2).clone();
                List<String> tickerParams = Arrays.asList(Arrays.copyOfRange(restTextList, 2, restTextList.length).clone());
                List<Date> convertedTime =
                        new ArrayList<>();
                for (String timeParam : timeParams) {
                    Date date = TimeUtil.stringToDate(timeParam);
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
                sbResponse.append("Trade started...🙃🙃🙃");
            } catch (ParseException | UserNotFoundException | EntityNotFoundException exp) {
                log.error(exp.getMessage());
                sbResponse.append("Incorrect enter!");
            }
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
