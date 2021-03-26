package com.lookfor.trading.algorithm;

import com.lookfor.trading.models.TickerData;
import com.lookfor.trading.services.TickersDateService;
import com.lookfor.trading.services.implementations.TickersDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@Async
public class Mainalgorithm {
    static final int N = 14;
    static int all_ticks=0;
    static float AvGain=0;
    static float AvLoss=0;
    static TickerData lastTicketDate=null;
    @Autowired
    private TickersDataServiceImpl tickersDataService;

    @Scheduled(fixedRate = 1000)
    public void doTask(){
        List<TickerData> tickerDataList = tickersDataService.getTickersDateAfterFirstTimeAndBeforeRealTime();
        lastTicketDate = tickerDataList.get(0);
        for (int i=1; i<tickerDataList.size(); i++){
            // float change = tickerDataList.get(i).getLastPrice() - lastTicketDate.getLastPrice();
            if (all_ticks<N){

            }
        }
    }

}
