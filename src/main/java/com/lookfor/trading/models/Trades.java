package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Trades entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date start;

    private Date stop;

    private BigDecimal balance;

    private boolean status;

    @ManyToOne
    private UsersTickers usersTickers;

    @OneToMany(mappedBy = "trades")
    private Set<TradesDeals> tradesDeals;
}
