package com.lookfor.trading.repositories;

import com.lookfor.trading.models.AlgorithmCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmCacheRepository extends CrudRepository<AlgorithmCache, String> {
}
