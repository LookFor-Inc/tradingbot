package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * UsersTickers entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_tickers")
public class UserTicker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Date date;

    @ManyToOne
    private User user;

    @Singular("data")
    @OneToMany(mappedBy = "usersTickers", cascade = CascadeType.ALL)
    private Set<TickerData> tickersData;

    @OneToMany(mappedBy = "usersTickers")
    private Set<Trades> trades;
}
