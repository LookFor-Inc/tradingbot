package com.lookfor.trading.parsers;

import com.lookfor.trading.models.TradeDeal;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collection;

@Component
public class TradeDealToCsv {
    public static final String HEADER_PATTERN = "<TIME>;<TYPE>;<PRICE>;<TOTAL>;<AMOUNT>;<BALANCE>";

    public File convert(Collection<TradeDeal> tradeDeals) {
        tradeDeals.stream().map(tradeDeal -> tradeDeal.getTime());
        // TODO
        return null;
    }
}
