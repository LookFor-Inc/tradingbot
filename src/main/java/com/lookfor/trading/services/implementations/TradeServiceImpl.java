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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final TradeRepository tradeRepository;
    private final UserTickerService userTickerService;

    @Override
    public void saveStartAndStopTime(String tickerName, int userId, Date start, Date stop) {
        Optional<UserTicker> userTickerOptional =
                userTickerService.findUserTickerByUserIdAndName(userId, tickerName);
        if (userTickerOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("User ticker not found %s", tickerName));
        }
        UserTicker userTicker = userTickerOptional.get();
        start.setDate(userTicker.getDate().getDate());
        start.setMonth(userTicker.getDate().getMonth());
        start.setYear(userTicker.getDate().getYear());
        stop.setDate(userTicker.getDate().getDate());
        stop.setMonth(userTicker.getDate().getMonth());
        stop.setYear(userTicker.getDate().getYear());
        Trade trade = Trade.builder()
                .start(start)
                .stop(stop)
                .balance(new BigDecimal(1_000_000))
                .userTicker(userTicker)
                .build();
        tradeRepository.save(trade);
    }
}
