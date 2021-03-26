package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.TickerData;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.repositories.TickerDataRepository;
import com.lookfor.trading.services.TickersDateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TickersDataServiceImpl implements TickersDateService {
    private final TickerDataRepository tickerDataRepository;

    @Transactional(readOnly = true)
    public List<TickerData> getTickersDataAfterFirstTimeAndBeforeRealTime(String lastTime, String currentTime, UserTicker userTicker){
        return tickerDataRepository.findTickersAfterLastTimeAndBeforeRealTime(currentTime, lastTime, userTicker);
    }

}
