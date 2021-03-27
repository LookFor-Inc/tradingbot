package com.lookfor.trading.repositories;

import com.lookfor.trading.models.TradeDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeDealRepository extends JpaRepository<TradeDeal, Long> {
}
