package com.lookfor.trading.services;

import com.lookfor.trading.models.Trade;

import java.util.Date;
import java.util.List;

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
    void saveStartAndStopTime(String tickerName, int userId, Date start, Date stop);

    /**
     * Get all trade by user ticker id
     * @param userTickerId id
     * @return list of trades
     */
    List<Trade> findAllByUserTickerId(Long userTickerId);
}
