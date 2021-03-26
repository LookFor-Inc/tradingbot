package com.lookfor.trading.services;

import com.lookfor.trading.models.UserTicker;

import java.util.List;
import java.util.Optional;

public interface UserTickerService {

    /**
     * Get list of all users tickers
     *
     * @return list of the users tickers
     */
    List<UserTicker> getAllUsersTickers();

    /**
     * Get list of all users tickers names
     *
     * @return list of all users tickers names
     */
    List<String> getAllUserTickerNames();

    /**
     * Check UserTicker existence
     *
     * @param userId User's telegram id
     * @param name Ticker name
     * @return existence
     */
    boolean existsByUserAndName(int userId, String name);

    /**
     * Save user's ticker data
     *
     * @param userTicker UserTicker object
     * @param userId User's telegram id
     */
    boolean save(UserTicker userTicker, int userId);

    /**
     * Find UserTicker by it's name
     *
     * @param name of the ticker
     * @return user ticker optional
     */
    Optional<UserTicker> findUserTickerByName(String name);
}
