package com.lookfor.trading.repositories;

import com.lookfor.trading.models.UsersTickers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UsersTickersRepository extends JpaRepository<UsersTickers, Long> {
}
