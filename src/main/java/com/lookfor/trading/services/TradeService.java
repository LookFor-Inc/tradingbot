package com.lookfor.trading.services;

import java.util.Date;

/**
 * Service interface for managing {@link com.lookfor.trading.models.Trade}
 */
public interface TradeService {
    /**
     * Save start and stop time for executing trades for user's ticker
     *
     * @param tickerName name of the ticker
     * @param userId id of the user
     * @param start time
     * @param stop time
     */
    void saveStartAndStopTime(String tickerName, int userId, Date start, Date stop);
}
