package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.lookfor.trading.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

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
            List<String> timeParams = Arrays.asList(Arrays.copyOfRange(restTextList, 0, 2).clone());
            List<String> tickerParams = Arrays.asList(Arrays.copyOfRange(restTextList, 2, restTextList.length).clone());
            List<Date> convertedTime =
                    timeParams.stream()
                            .map(TimeUtil::stringToDate)
                            .collect(Collectors.toList());
            tickerParams.forEach(ticker ->
                    tradeService.saveStartAndStopTime(ticker, convertedTime.get(0), convertedTime.get(1))
            );
            System.out.println(convertedTime);
            System.out.println(tickerParams);
        }

        return SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode(ParseMode.MARKDOWN)
                .text(sbResponse.toString())
                .build();
    }
}
