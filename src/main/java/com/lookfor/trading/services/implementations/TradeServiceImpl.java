package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.repositories.TradeRepository;
import com.lookfor.trading.services.TradeService;
import com.lookfor.trading.services.UserTickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final UserTickerService userTickerService;

    @Override
    @Transactional
    public void saveStartAndStopTime(String tickerName, Date start, Date stop) {
        Optional<UserTicker> userTickerOptional =
                userTickerService.findUserTickerByName(tickerName);
        if (userTickerOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("User ticker not found %s", tickerName));
        }
        UserTicker userTicker = userTickerOptional.get();
        Trade trade = Trade.builder()
                .start(start)
                .stop(stop)
                .balance(new BigDecimal(1_000_000))
                .build();
        tradeRepository.save(trade);
        System.out.println(userTicker.getTrades());
        userTicker.getTrades().add(trade);
    }

    @Override
    public boolean isTimeInPeriod(Date date) {
        return false;
    }

    @Override
    @Transactional
    public List<Trade> getRunningTrades(Date format){
        //List<Trade> tradeList = tradeRepository.findAll();
        //List<Trade> running = tradeList.forEach();
        return tradeRepository.findRunningTrades(format);
    }

    /*@Override
    @Transactional(readOnly = true)
    public boolean isTimeInPeriod(Date date) {
        return date.before(tradeRepository.findByStartTime)
    }*/


}
