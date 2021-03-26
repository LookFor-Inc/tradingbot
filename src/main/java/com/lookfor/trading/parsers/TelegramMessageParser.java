package com.lookfor.trading.parsers;

import com.lookfor.trading.config.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class TelegramMessageParser extends Thread {
    private final TelegramBot telegramBot;
    private final Update update;
    private final ApplicationContext appContext;

    @Override
    public void run() {

    }
}
