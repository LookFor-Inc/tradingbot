package com.lookfor.trading.repositories;

import com.lookfor.trading.models.User;
import com.lookfor.trading.models.UserTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserTickerRepository extends JpaRepository<UserTicker, Long> {
    Optional<UserTicker> findByUserAndName(User user, String name);
    
    List<UserTicker> findAllByUserId(int userId);
}
