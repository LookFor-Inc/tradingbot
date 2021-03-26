package com.lookfor.trading.services;

import java.util.List;

public interface UsersTickersService {

    /**
     * Get list of all users tickers names
     *
     * @return list of the users tickers names
     */
    List<String> getAllUsersTickersNames();
}
