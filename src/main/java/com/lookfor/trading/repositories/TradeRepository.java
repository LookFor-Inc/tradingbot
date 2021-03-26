package com.lookfor.trading.repositories;

import com.lookfor.trading.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findAllByUserTickerId(Long userTickerId);
}
