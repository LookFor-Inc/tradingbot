package com.lookfor.trading.services;

import com.lookfor.trading.models.UserTicker;

import java.util.List;
import java.util.Optional;

public interface UserTickerService {
    /**
     * Find list of all users tickers by user id
     *
     * @return list of the users tickers by user id
     */
    List<UserTicker> findAllByUserId(int userId);

    /**
     * Get list of all users tickers names
     *
     * @return list of all users tickers names by user id
     */
    List<String> findAllUserTickerNames(int userId);

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
     * @param userId id of the user
     * @param name of the ticker
     * @return user ticker optional
     */
    Optional<UserTicker> findUserTickerByUserIdAndName(int userId, String name);

    /**
     * Find UserTicker by it's name
     *
     * @param name of the ticker
     * @return user ticker optional
     */
    Optional<UserTicker> findUserTickerByName(String name);
}
