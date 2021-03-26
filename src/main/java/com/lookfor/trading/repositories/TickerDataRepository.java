package com.lookfor.trading.repositories;

import com.lookfor.trading.models.TickerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TickerDataRepository extends JpaRepository<TickerData, Integer> {

    @Query(value = "select t FROM TickerData t where t.time < :realTime AND t.time > :lastTime")
    List<TickerData> findTickersAfterLastTimeAndBeforeRealTime(String realTime, String lastTime);
}
