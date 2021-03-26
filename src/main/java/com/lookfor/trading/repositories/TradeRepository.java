package com.lookfor.trading.repositories;

import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.UserTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    boolean existsByStartAndStopAndUserTicker(Date start, Date stop, UserTicker userTicker);
}
