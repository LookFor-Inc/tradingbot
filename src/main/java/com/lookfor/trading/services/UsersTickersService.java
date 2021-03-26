package com.lookfor.trading.services;

import com.lookfor.trading.models.UsersTickers;

import java.util.List;

public interface UsersTickersService {

    /**
     * Get list of all users tickers
     *
     * @return list of the users tickers
     */
    List<UsersTickers> getAllUsersTickers();

    /**
     * Get list of all users tickers names
     *
     * @return list of all users tickers names
     */
    List<String> getAllUsersTickersNames();
}
