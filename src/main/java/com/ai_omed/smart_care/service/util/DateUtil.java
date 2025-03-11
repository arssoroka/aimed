package com.ai_omed.smart_care.service.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalTime parseTime(String str) {
        if (str == null) {
            return null;
        }
        return LocalTime.parse(str, DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static LocalDateTime parseDateTime(String str) {
        if (str == null) {
            return null;
        }
        return LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


    public static String format(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static String format(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String format(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalDateTime combineDateWithTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            return null;
        }
        String localDateTimeStr = DateUtil.format(date) + "T" + DateUtil.format(time);
        return parseDateTime(localDateTimeStr);
    }

}
