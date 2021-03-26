package com.lookfor.trading.repositories;

import com.lookfor.trading.models.UsersTickers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UsersTickersRepository extends JpaRepository<UsersTickers, Long> {

    @Query(value = "SELECT Name FROM UsersTickers", nativeQuery = true)
    List<String> findAllNames();
}
