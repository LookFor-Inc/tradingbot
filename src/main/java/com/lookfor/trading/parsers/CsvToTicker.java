package com.lookfor.trading.parsers;

import com.lookfor.trading.exceptions.IncorrectRequestException;
import com.lookfor.trading.models.TickerData;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;

@Component
public class CsvToTicker {
    public static final String HEADER_PATTERN = "<TICKER>;<PER>;<DATE>;<TIME>;<LAST>;<VOL>";

    public UserTicker convert(File csv) throws IncorrectRequestException {
        UserTicker.UserTickerBuilder builder = UserTicker.builder();

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            String line = reader.readLine();

            if (line == null || !line.equals(HEADER_PATTERN)) {
                throw new IncorrectRequestException("Incorrect ticker file format!");
            }
            line = reader.readLine();
            String[] arr = null;

            while (line != null) {
                arr = line.split(";");
                TickerData data = TickerData.builder()
                        .date(arr[2])
                        .time(arr[3])
                        .lastPrice(arr[4])
                        .build();

                builder.data(data);
                line = reader.readLine();
            }

            if (arr == null) {
                throw new IncorrectRequestException("Internal converter error!");
            }

            builder
                    .name(arr[0])
                    .date(DateUtil.stringToDate(arr[2]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.build();
    }
}
