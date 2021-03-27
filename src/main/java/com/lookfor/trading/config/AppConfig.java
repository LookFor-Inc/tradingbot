package com.lookfor.trading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@EnableScheduling
public class AppConfig {
    @Autowired
    private CacheManager cacheManager;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @PreDestroy
    public void clearCache() {
        cacheManager.getCacheNames()
                .parallelStream()
                .forEach(n -> cacheManager.getCache(n).clear());
    }
}
