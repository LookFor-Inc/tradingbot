package com.lookfor.trading.repositories;

import com.lookfor.trading.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query("SELECT tr FROM Trade tr, UserTicker ut WHERE tr.userTicker.user.id = :userId")
    List<Trade> findAllByUserId(int userId);

    @Query("SELECT tr FROM Trade tr, UserTicker ut WHERE tr.userTicker.user.id = :userId AND tr.status = :status")
    List<Trade> findAllByUserIdAndStatus(int userId, boolean status);
}
