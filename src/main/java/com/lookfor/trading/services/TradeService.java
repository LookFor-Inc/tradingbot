package com.lookfor.trading.services;

import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.TradeDeal;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Service interface for managing {@link com.lookfor.trading.models.Trade}
 */
public interface TradeService {

    /**
     * Is need to start algorithm
     *
     * @param date Current date
     */
    boolean isTimeInPeriod(Date date);

    List<Trade> getRunningTrades(Date format);

    void saveStartAndStopTime(String tickerName, int userId, Date start, Date stop) throws IncorrectRequestException, EntityNotFoundException;

    /**
     * Get all trade by user ticker id
     * @param userTickerId id
     * @return list of trades
     */
    List<Trade> findAllByUserTickerId(long userTickerId);

    /**
     * Find all trade deals by trade id
     *
     * @param tradeId id of the trade
     * @return all trade deals
     */
    Set<TradeDeal> findAllTradeDealsByTradeId(long tradeId) throws EntityNotFoundException;

    void save(Trade trade);
}
