package com.lookfor.trading.services;

import com.lookfor.trading.algorithm.TradeAlgorithm;
import com.lookfor.trading.models.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeScheduler {
    private final TradeService tradeService;
    private final TradeAlgorithm tradeAlgorithm;

    /**
     * Check trades by current time and start/stop time every 1 sec
     *
     * @throws ParseException
     */
    @Scheduled(fixedRate = 1000)
    public void checkTrades() throws ParseException {
        StringBuilder time = new StringBuilder();
        Calendar calendar = new GregorianCalendar();

        time.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(calendar.get(Calendar.MINUTE)).append(":")
                .append(calendar.get(Calendar.SECOND));

        Date date = new Date(System.currentTimeMillis());
        date.setYear(70);
        date.setMonth(Calendar.JANUARY);
        date.setDate(1);

        List<Trade> tradeList = tradeService.getRunningTrades(date);

        tradeList.forEach(tradeAlgorithm::doTask);
    }
}
