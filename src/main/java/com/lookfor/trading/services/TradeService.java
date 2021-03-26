package com.lookfor.trading.services;

import com.lookfor.trading.models.Trade;

import java.util.Date;
import java.util.List;

/**
 * Service interface for managing {@link Trade}
 */
public interface TradeService {
    /**
     * Save start and stop time for executing trades for ticker
     *
     * @param tickerName name of the ticker
     * @param start time
     * @param stop time
     */
    void saveStartAndStopTime(String tickerName, Date start, Date stop);

    /**
     * Is need to start algorithm
     *
     * @param date Current date
     */
    boolean isTimeInPeriod(Date date);

    List<Trade> getRunningTrades(String format);
}
