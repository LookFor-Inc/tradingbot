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
public class UsersTickers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Date date;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "usersTickers")
    private Set<TickersData> tickersData;

    @OneToMany(mappedBy = "usersTickers")
    private Set<Trades> trades;
}
