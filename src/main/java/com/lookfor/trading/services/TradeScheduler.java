package com.lookfor.trading.services;

import com.lookfor.trading.algorithm.MainAlgorithm;
import com.lookfor.trading.models.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeScheduler {
    private final TradeService tradeService;
    private final MainAlgorithm mainalgorithm;

    @Scheduled(fixedRate = 1000)
    public void checkTrades() throws ParseException {
        SimpleDateFormat currentTime = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String format = "2021-01-21 11:15:11";
        Date date1 = currentTime.parse(format);
        List<Trade> tradeList = tradeService.getRunningTrades(date1);//currentTime.format(date));

        tradeList.forEach(mainalgorithm::doTask);
    }
}
