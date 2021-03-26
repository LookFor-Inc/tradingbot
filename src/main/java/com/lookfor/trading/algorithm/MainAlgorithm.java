package com.lookfor.trading.algorithm;

import com.lookfor.trading.models.AlgorithmCache;
import com.lookfor.trading.models.TickerData;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.repositories.AlgorithmCacheRepository;
import com.lookfor.trading.services.implementations.TickersDataServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MainAlgorithm {

    private final AlgorithmCacheRepository algorithmCacheRepository;

    static final int N = 14;
    static int allTicks = 0;
    static float AvGain = 0;
    static float AvLoss = 0;
    static TickerData lastTicketDate = null;

    private final TickersDataServiceImpl tickersDataService;

    @Async
    public void doTask(Trade trade) {
        Optional<AlgorithmCache> algorithmCacheOptional = algorithmCacheRepository.findById(String.valueOf(trade.getId()));
        AlgorithmCache algorithmCache;
        if (algorithmCacheOptional.isEmpty()){
            algorithmCache = new AlgorithmCache();
        } else {
            algorithmCache = algorithmCacheOptional.get();
        }

        float RSI;

        // algorithmCache.getLastTicketDate().getTime();
        String lastTime;
        if (algorithmCache.getLastTicketDate() == null) {
            lastTime = trade.getStart().toString(); // ToDo переобразовать в формать HHmmss
        } else {
            lastTime=algorithmCache.getLastTicketDate().getTime();
        }

        String currentTime = "101010";
        Calendar calendar = new GregorianCalendar();
        /*realTime
                .concat(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)))
                .concat(String.valueOf(calendar.get(Calendar.MINUTE)))
                .concat(String.valueOf(calendar.get(Calendar.SECOND)));*/

        List<TickerData> tickerDataList = tickersDataService.getTickersDataAfterFirstTimeAndBeforeRealTime(lastTime, currentTime, trade.getUserTicker());

        if (algorithmCache.getLastTicketDate() == null) {
            algorithmCache.setLastTicketDate(tickerDataList.get(0));
            tickerDataList.remove(0);
        }

        for (int i = 0; i < tickerDataList.size(); i++) {
            algorithmCache.incrementAllTicks();
            float change = Float.parseFloat(tickerDataList.get(i).getLastPrice()) - Float.parseFloat(algorithmCache.getLastTicketDate().getLastPrice());

            if (change > 0) {
                algorithmCache.setAvGain((algorithmCache.getAvGain() * (i - 1) + change) / i);
                algorithmCache.setAvLoss((float) (algorithmCache.getAvLoss() * (i - 1.0)/i));
            } else {
                algorithmCache.setAvGain((float) (algorithmCache.getAvGain() * (i - 1.0)/i));
                algorithmCache.setAvLoss((algorithmCache.getAvLoss() * (i - 1) + change) / i);
            }

            if (algorithmCache.getAllTicks()>N){
                RSI = (100 - (100/ (1+ algorithmCache.getAvGain()/algorithmCache.getAvLoss())));
                System.out.println(RSI);
                if (RSI > 70){
                    //вызов метода на продажу акций (передача TickersDate)
                } else if(RSI < 30){
                    // вызов метода на покупку (проверка на баланс, осуществления покупки)
                }
            }
            algorithmCacheRepository.save(algorithmCache);
        }
    }

}
