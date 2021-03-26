package com.lookfor.trading.repositories;

import com.lookfor.trading.models.UserTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserTickerRepository extends JpaRepository<UserTicker, Long> {
    Optional<UserTicker> findByName(String name);

    @Query("SELECT CASE WHEN COUNT(ut) > 0 THEN TRUE ELSE FALSE END FROM UserTicker ut WHERE ut.user.id = :userId AND ut.name = :name")
    boolean existsByUserAndName(int userId, String name);
}
