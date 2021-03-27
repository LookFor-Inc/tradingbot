package com.lookfor.trading.services;

import com.lookfor.trading.models.TradeDeal;

/**
 * Service interface for managing {@link com.lookfor.trading.models.TradeDeal}
 */
public interface TradeDealService {
    void save(TradeDeal tradeDeal);
}
