package com.lookfor.trading.parsers;

import com.lookfor.trading.config.TelegramBot;
import com.lookfor.trading.interfaces.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DocumentParser {

    @Async
    public void parse(TelegramBot bot, Message message) {
        String fileId = message.getDocument().getFileId();

        try {
            File filePath = getFilePath(bot, fileId);
            if (!filePath.getFilePath().endsWith(".csv")) {
                bot.sendToUser(message.getChatId(), "Wrong file format, csv required!");
                return;
            }

            var f = bot.downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            bot.sendToUser(message.getChatId(), "Oops! Error processing your file :(");
        }

    }

    public File getFilePath(TelegramBot bot, String fileId) throws TelegramApiException {
        GetFile getFile = new GetFile(fileId);
        return bot.execute(getFile);
    }
}
