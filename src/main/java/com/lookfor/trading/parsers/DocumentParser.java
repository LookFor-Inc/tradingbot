package com.lookfor.trading.parsers;

import com.lookfor.trading.config.TelegramBot;
import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.services.UserTickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocumentParser {
    private final CsvToTicker converter;
    private final UserTickerService userTickerService;

    @Async
    public void parse(TelegramBot bot, Message message) {
        String fileId = message.getDocument().getFileId();
        int userId = message.getFrom().getId();

        try {
            File filePath = getFilePath(bot, fileId);

            if (!filePath.getFilePath().endsWith(".csv")) {
                bot.sendToUser(userId, "Wrong file format, csv required!");
                return;
            }

            var csv = bot.downloadFile(filePath);
            UserTicker userTicker = converter.convert(csv);

            if (userTickerService.existsByUserAndName(userId, userTicker.getName())) {
                bot.sendToUser(userId,
                        String.format("You can not upload multiple csv with the same ticker name: %s!", userTicker.getName())
                );
                return;
            }

            boolean status = userTickerService.save(converter.convert(csv), userId);

            if (status) {
                bot.sendToUser(userId, "Ticker successfully saved!");
            }
        } catch (TelegramApiException e) {
            bot.sendToUser(userId, "Oops! Error processing your file :(");
        } catch (IncorrectRequestException e) {
            bot.sendToUser(userId, e.getMessage());
        }

    }

    public File getFilePath(TelegramBot bot, String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile(fileId);
        return bot.execute(getFile);
    }
}
