package com.lookfor.trading.services;

import com.lookfor.trading.models.Trade;

import java.util.Date;

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
}
