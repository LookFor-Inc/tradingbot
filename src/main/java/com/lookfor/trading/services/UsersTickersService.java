package com.lookfor.trading.services;

import com.lookfor.trading.models.UsersTickers;

import java.util.List;

public interface UsersTickersService {

    /**
     * Get list of all users tickers names
     *
     * @return list of the users tickers names
     */
    List<String> getAllUsersTickersNames();

    /**
     * Save user's ticker data
     *
     * @param userTicker UserTicker object
     * @param userId User's telegram id
     */
    boolean save(UsersTickers userTicker, int userId);
}
