package com.lookfor.trading.algorithm;

import com.lookfor.trading.models.AlgorithmCache;
import com.lookfor.trading.models.TickerData;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.TradeDeal;
import com.lookfor.trading.repositories.AlgorithmCacheRepository;
import com.lookfor.trading.services.TradeDealService;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.services.implementations.TickersDataServiceImpl;
import com.lookfor.trading.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TradeAlgorithm {
    private final TradeDealService tradeDealService;
    private final TradeService tradeService;
    private final AlgorithmCacheRepository algorithmCacheRepository;
    private final TickersDataServiceImpl tickersDataService;

    static final int N = 14;

    /**
     * Async algorithm for trading
     *
     * @param trade source
     */
    @Async
    public void doTask(Trade trade) {
        Optional<AlgorithmCache> algorithmCacheOptional = algorithmCacheRepository.findById(String.valueOf(trade.getId()));
        AlgorithmCache algorithmCache;
        if (algorithmCacheOptional.isEmpty()) {
            algorithmCache = new AlgorithmCache();
        } else {
            algorithmCache = algorithmCacheOptional.get();
        }

        StringBuilder startTimeCurrentFormat = new StringBuilder();
        if (algorithmCache.getTime() == null) {
            StringBuilder startTimeBadFormat = new StringBuilder(trade.getStart().toString().split(" ")[1].split("\\.")[0]);
            for (String str : startTimeBadFormat.toString().split(":")) {
                startTimeCurrentFormat.append(str);
            }
        } else {
            startTimeCurrentFormat.append(algorithmCache.getTime());
        }

        StringBuilder currentTime = new StringBuilder();
        Calendar calendar = new GregorianCalendar();

        currentTime.append(calendar.get(Calendar.HOUR_OF_DAY))
                .append(calendar.get(Calendar.MINUTE))
                .append(calendar.get(Calendar.SECOND));

        List<TickerData> tickerDataList = tickersDataService.getTickersDataAfterFirstTimeAndBeforeRealTime(startTimeCurrentFormat.toString(), currentTime.toString(), trade.getUserTicker());

        if (algorithmCache.getTime() == null && algorithmCache.getDate() == null && algorithmCache.getLastPrice() == null && tickerDataList.size() != 0) {
            TickerData lastTickerDate = tickerDataList.get(0);
            algorithmCache.setDate(lastTickerDate.getDate());
            algorithmCache.setLastPrice(lastTickerDate.getLastPrice().toString());
            algorithmCache.setTime(lastTickerDate.getTime());

            algorithmCache.setId(String.valueOf(trade.getId()));
            tickerDataList.remove(0);
        }

        for (TickerData tickerData : tickerDataList) {
            algorithmCache.incrementAllTicks();
            float change = tickerData.getLastPrice().floatValue() - Float.parseFloat(algorithmCache.getLastPrice());

            if (change > 0) {
                algorithmCache.setAvGain((algorithmCache.getAvGain() * (algorithmCache.getAllTicks() - 1) + change) / (algorithmCache.getAllTicks()));
                algorithmCache.setAvLoss((algorithmCache.getAvLoss() * (algorithmCache.getAllTicks() - 1) / (algorithmCache.getAllTicks())));
            } else {
                algorithmCache.setAvGain((algorithmCache.getAvGain() * (algorithmCache.getAllTicks() - 1) / (algorithmCache.getAllTicks())));
                algorithmCache.setAvLoss((algorithmCache.getAvLoss() * (algorithmCache.getAllTicks() - 1) + Math.abs(change)) / (algorithmCache.getAllTicks()));
            }

            if (algorithmCache.getAllTicks() > N) {
                float RSI = (100 - (100 / (1 + algorithmCache.getAvGain() / algorithmCache.getAvLoss())));

                if (RSI > 70) {
                    BigDecimal price = new BigDecimal(algorithmCache.getLastPrice());
                    long amount = trade.getTotalAmount();// getBalance().divide(price, RoundingMode.DOWN).longValue();

                    if (amount != 0) {
                        TradeDeal tradeDeal = TradeDeal.builder()
                                .time(DateTimeUtil.dateToString(DateTimeUtil.getNowDate(), DateTimeUtil.PatternType.HH_MM_SS_TOG))
                                .type(TradeDeal.Type.SALE)
                                .price(price)
                                .amount(amount)
                                .trade(trade)
                                .build();


                        trade.setBalance(trade.getBalance().add(price.multiply(new BigDecimal(amount))));
                        trade.setTotalAmount(0);
                        tradeDeal.setBalance(trade.getBalance());
                        tradeService.save(trade);
                        tradeDealService.save(tradeDeal);
                    }
                    //вызов метода на продажу акций (передача TickersDate)
                } else if (RSI < 30) {
                    // вызов метода на покупку (проверка на баланс, осуществления покупки)
                    BigDecimal price = new BigDecimal(algorithmCache.getLastPrice());
                    long amount = trade.getBalance().divide(price, RoundingMode.DOWN).longValue();

                    if (amount != 0) {
                        TradeDeal tradeDeal = TradeDeal.builder()
                                .time(DateTimeUtil.dateToString(DateTimeUtil.getNowDate(), DateTimeUtil.PatternType.HH_MM_SS_TOG))
                                .type(TradeDeal.Type.PURCHASE)
                                .price(price)
                                .amount(amount)
                                .trade(trade)
                                .build();

                        trade.setBalance(trade.getBalance().subtract(price.multiply(new BigDecimal(amount))));
                        trade.setTotalAmount(trade.getTotalAmount() + amount);
                        tradeDeal.setBalance(trade.getBalance());
                        tradeService.save(trade);
                        tradeDealService.save(tradeDeal);
                    }
                }
            }
            algorithmCache.setDate(tickerData.getDate());
            algorithmCache.setLastPrice(tickerData.getLastPrice().toString());
            algorithmCache.setTime(tickerData.getTime());
        }
        algorithmCacheRepository.save(algorithmCache);
    }

}
