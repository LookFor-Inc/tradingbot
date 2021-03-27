package com.lookfor.trading.parsers;

import com.lookfor.trading.models.TradeDeal;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class TradeDealToCsv {
    public static final String HEADER_PATTERN = "<TIME>;<TYPE>;<PRICE>;<TOTAL>;<AMOUNT>;<BALANCE>";

    public File convert(Collection<TradeDeal> tradeDeals) throws IOException {
        File csvFile = File.createTempFile("export", ".csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write(HEADER_PATTERN);

        List<String[]> data = new ArrayList<>();
        tradeDeals.forEach(tradeDeal -> {
            BigDecimal price = tradeDeal.getPrice();
            BigDecimal amount = new BigDecimal(tradeDeal.getAmount());
            data.add(new String[]{
                    tradeDeal.getTime(),
                    tradeDeal.getType().getCode(),
                    String.valueOf(price),
                    String.valueOf(amount.multiply(price)),
                    String.valueOf(amount),
                    String.valueOf(tradeDeal.getBalance())
            });
        });
        for (String[] datum : data) {
            writer.write('\n');
            writer.write(convertToCSV(datum));
        }
        writer.close();
        return csvFile;
    }

    private String convertToCSV(String[] data) {
        return String.join(";", data);
    }
}
