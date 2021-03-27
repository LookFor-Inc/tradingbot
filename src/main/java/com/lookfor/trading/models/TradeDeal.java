package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TradeDeal entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trades_deals")
public class TradeDeal {

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

    private String time;

    @Digits(integer = 9, fraction = 9)
    private BigDecimal balance;

    @Digits(integer = 9, fraction = 9)
    private BigDecimal price;

    private long amount;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Trade trade;
}
