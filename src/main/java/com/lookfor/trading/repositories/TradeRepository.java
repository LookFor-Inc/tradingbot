package com.lookfor.trading.repositories;

import com.lookfor.trading.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "select t from Trade t where t.start < :format AND t.stop> :format")
    List<Trade> findRunningTrades(String format);
}
