package com.lookfor.trading.services.implementations;

import com.lookfor.trading.repositories.TradeDealRepository;
import com.lookfor.trading.services.TradeDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeDealServiceImpl implements TradeDealService {
    private final TradeDealRepository tradeDealRepository;
    
}
