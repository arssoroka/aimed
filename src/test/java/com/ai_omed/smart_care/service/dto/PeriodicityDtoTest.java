package com.ai_omed.smart_care.service.dto;

import com.ai_omed.smart_care.service.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PeriodicityDtoTest {
    private static final LocalDateTime DATE_TIME = DateUtil.parseDateTime("2025-03-10T22:59:01");

    @Test
    void calculateValuesForScheluler_DAY() {
        PeriodicityDto periodicityDto = PeriodicityDto.calculateValuesForScheluler(Periodicity.DAY, DATE_TIME);

        Assertions.assertEquals(PeriodicityDto.builder()
                .periodicitySeconds(60*60*24)
                .initDelaySeconds(3659)
                .build(), periodicityDto
        );

    }

    @Test
    void calculateValuesForScheluler_WEEK() {
        PeriodicityDto periodicityDto = PeriodicityDto.calculateValuesForScheluler(Periodicity.WEEK, DATE_TIME);

        Assertions.assertEquals(PeriodicityDto.builder()
                .periodicitySeconds(60*60*24*7)
                .initDelaySeconds(3659)
                .build(), periodicityDto
        );

    }

    @Test
    void calculateValuesForScheluler_HOUR() {
        PeriodicityDto periodicityDto = PeriodicityDto.calculateValuesForScheluler(Periodicity.HOUR, DATE_TIME);

        Assertions.assertEquals(PeriodicityDto.builder()
                .periodicitySeconds(60*60)
                .initDelaySeconds(59)
                .build(), periodicityDto
        );

    }

}