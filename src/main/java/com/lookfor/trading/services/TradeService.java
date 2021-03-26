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
     * Find all user's trades by user id
     *
     * @param userId User's telegram id
     * @return list of trades
     */
    List<Trade> findAllByUserId(int userId);

    /**
     * Find all user's trades by user id its status
     *
     * @param userId User's telegram id
     * @return list of trades
     */
    List<Trade> findAllByUserIdAndStatus(int userId, boolean status);
}
