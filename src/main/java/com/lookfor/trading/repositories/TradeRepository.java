package com.lookfor.trading.repositories;

import com.lookfor.trading.models.Trade;
import com.lookfor.trading.models.UserTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "select t from Trade t where :format between t.start and t.stop")
    List<Trade> findRunningTrades(Date format);
    boolean existsByStartAndStopAndUserTicker(Date start, Date stop, UserTicker userTicker);

    List<Trade> findAllByUserTickerId(Long userTickerId);
    
    @Query("SELECT tr FROM Trade tr, UserTicker ut WHERE tr.userTicker.user.id = :userId")
    List<Trade> findAllByUserId(int userId);

    @Query("SELECT tr FROM Trade tr, UserTicker ut WHERE tr.userTicker.user.id = :userId AND tr.status = :status")
    List<Trade> findAllByUserIdAndStatus(int userId, boolean status);
}
