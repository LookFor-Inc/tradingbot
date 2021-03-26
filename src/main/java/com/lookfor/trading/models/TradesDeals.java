package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TradesDeals entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TradesDeals {

    public enum Type {
        PURCHASE, SALE;

        private final String code;

        Type() {
            this.code = name().toLowerCase();
        }

        public String getCode() {
            return code;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date time;

    private BigDecimal price;

    private long amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Trades trades;
}
