package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TickersData entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickers_data")
public class TickersData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UsersTickers usersTickers;

    private String date;

    private String time;

    private BigDecimal lastPrice;
}
