package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * TickerData entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "tickers_data")
public class TickerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UserTicker userTicker;

    private String date;

    private String time;

    @Digits(integer = 9, fraction = 9)
    private BigDecimal lastPrice;
}
