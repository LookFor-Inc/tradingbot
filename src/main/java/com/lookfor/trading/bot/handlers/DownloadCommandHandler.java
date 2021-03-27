package com.lookfor.trading.bot.handlers;

import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.interfaces.MessageSender;
import com.lookfor.trading.interfaces.RootCommandHandler;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.TradeDeal;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.parsers.TradeDealToCsv;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.services.UserTickerService;
import com.lookfor.trading.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.lookfor.trading.utils.DateTimeUtil.dateToString;
import static com.lookfor.trading.utils.TextMessageUtil.getRestOfTextMessageWithoutCommand;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadCommandHandler implements RootCommandHandler<SendDocument> {
    private final UserTickerService userTickerService;
    private final TradeService tradeService;
    private final TradeDealToCsv tradeDealToCsv;

    private final MessageSender messageSender;

    @Override
    public SendDocument doParse(Update update) throws TelegramApiException {
        Message message = getReceivedMessage(update);
        String restOfTextMessage = getRestOfTextMessageWithoutCommand(message.getText());
        StringBuilder sbResponse = new StringBuilder();

        if (restOfTextMessage.isEmpty()) {
            List<UserTicker> userTickers = userTickerService.findAllByUserId(message.getFrom().getId());
            if (userTickers.isEmpty()) {
                sbResponse.append("😔 You do not have any available tickers 😔");
            } else {
                for (UserTicker userTicker : userTickers) {
                    List<Trade> trades = tradeService.findAllByUserTickerId(userTicker.getId());
                    sbResponse.append("📈 ").append(userTicker.getName()).append('\n');
                    if (trades.isEmpty()) {
                        sbResponse.append("You do not have any available trades 😔");
                    } else {
                        sbResponse.append("You can download CSV file 👌\n");
                        trades.forEach(trade -> sbResponse
                                .append("🗒 Available trades:\n")
                                .append(dateToString(trade.getStart(), DateTimeUtil.PatternType.HH_MM_SS_COLON))
                                .append("-")
                                .append(dateToString(trade.getStop(), DateTimeUtil.PatternType.HH_MM_SS_COLON))
                                .append("\n/download #")
                                .append(trade.getId())
                                .append("\n\n"));
                    }
                }
            }
        } else if (restOfTextMessage.startsWith("#")) {
            try {
                long tradeId = Long.parseLong(restOfTextMessage.substring(1));
                Set<TradeDeal> tradeDeals = tradeService.findAllTradeDealsByTradeId(tradeId);
                File csvFile = tradeDealToCsv.convert(tradeDeals);
                InputFile inputFile = new InputFile();
                inputFile.setMedia(csvFile);

                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(String.valueOf(message.getChatId()));
                sendDocument.setReplyToMessageId(message.getMessageId());
                sendDocument.setDocument(new InputFile(csvFile, "export.csv"));

                return sendDocument;
            } catch (NumberFormatException | EntityNotFoundException | IncorrectRequestException | IOException exp) {
                log.error(exp.getMessage());
                sbResponse.append(exp.getMessage());
            }
        } else {
            sbResponse.append("‼️ Send command /download <trade id> to export CSV file ‼️");
        }

        messageSender.sendToUser(message.getFrom().getId(), sbResponse.toString());
        return null;
    }
}
