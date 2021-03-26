package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Trade entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date start;

    private Date stop;

    private BigDecimal balance;

    private boolean status;

    @ManyToOne
    private UserTicker userTicker;

    @OneToMany(mappedBy = "trade")
    private Set<TradeDeal> tradeDeals;
}
