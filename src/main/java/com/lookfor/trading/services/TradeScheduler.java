package com.lookfor.trading.services;

import com.lookfor.trading.algorithm.MainAlgorithm;
import com.lookfor.trading.models.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeScheduler {
    private final TradeService tradeService;
    private final MainAlgorithm mainalgorithm;

    @Scheduled(fixedRate = 1000)
    public void checkTrades(){
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        List<Trade> tradeList = tradeService.getRunningTrades(currentTime.format(date));

        tradeList.forEach(mainalgorithm::doTask);
    }
}
