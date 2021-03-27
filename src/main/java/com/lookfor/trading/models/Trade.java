package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
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

    @Digits(integer = 9, fraction = 9)
    private BigDecimal balance;

    private boolean status;

    // private long totalAmount;

    @ManyToOne
    private UserTicker userTicker;

    @OneToMany(mappedBy = "trade")
    private Set<TradeDeal> tradeDeals;
}
