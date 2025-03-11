package com.ai_omed.smart_care.service.util;

import com.ai_omed.smart_care.service.dto.RecurringPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecurringPatternParser {
    private static final Map<String, Integer> DAY_OF_WEEK_MAP =
            Map.of( "Monday", 1,
                    "Tuesday", 2,
                    "Wednesday", 3,
                    "Thursday", 4,
                    "Friday", 5,
                    "Saturday", 6,
                    "Sunday", 7);

    public static List<RecurringPattern> parse(String recurringStr){
        Integer dayOfWeek = null;
        if (recurringStr.startsWith("every day")){
            dayOfWeek = 0;
        } else if (recurringStr.startsWith("every")) {
            Pattern pattern = Pattern.compile("every (\\w+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(recurringStr);
            if (matcher.find()){
                dayOfWeek = DAY_OF_WEEK_MAP.get(matcher.group(1));
            }
        }
        if (dayOfWeek == null){
            return null;
        }

        Pattern pattern = Pattern.compile("(at|and) ([0-9][0-9]:[0-9][0-9])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(recurringStr);
        List<RecurringPattern> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(
                RecurringPattern.builder()
                        .dayOfWeek(dayOfWeek)
                        .startTime(DateUtil.parseTime(matcher.group(2)))
                        .endTime(DateUtil.parseTime(matcher.group(2)))
                        .build()
            );
        }

        pattern = Pattern.compile("from ([0-9][0-9]:[0-9][0-9]) to ([0-9][0-9]:[0-9][0-9])", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(recurringStr);
        while (matcher.find()) {
            result.add(
                    RecurringPattern.builder()
                            .dayOfWeek(dayOfWeek)
                            .startTime(DateUtil.parseTime(matcher.group(1)))
                            .endTime(DateUtil.parseTime(matcher.group(2)))
                            .build()
            );
        }

        return result;
    }
}
