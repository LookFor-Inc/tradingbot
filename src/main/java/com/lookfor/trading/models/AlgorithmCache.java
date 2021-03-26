package com.lookfor.trading.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("AlgorithmCache")
@Getter
@Setter
public class AlgorithmCache implements Serializable {
    private String id;
    private int allTicks;
    private float AvGain;
    private float AvLoss;
    private TickerData lastTicketDate;

    public void incrementAllTicks(){
        allTicks++;
    }
}
