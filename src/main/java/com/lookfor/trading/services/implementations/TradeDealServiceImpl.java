package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.TradeDeal;
import com.lookfor.trading.repositories.TradeDealRepository;
import com.lookfor.trading.services.TradeDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeDealServiceImpl implements TradeDealService {
    private final TradeDealRepository tradeDealRepository;

    @Override
    @Transactional
    public void save(TradeDeal tradeDeal) {
        tradeDealRepository.save(tradeDeal);
    }
}
