package com.ai_omed.smart_care.service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class PeriodicityDto {
    private long initDelaySeconds;
    private long periodicitySeconds;

    public static PeriodicityDto calculateValuesForScheluler(Periodicity periodicity, LocalDateTime dateTime) {

        long initDelaySeconds = 0;
        long periodicitySeconds = 0;

        if(periodicity == Periodicity.DAY){
            initDelaySeconds = ChronoUnit.SECONDS.between(dateTime, dateTime.toLocalDate().atStartOfDay().plusDays(1));
            periodicitySeconds = 60*60*24;
        } else if (periodicity == Periodicity.WEEK) {
            initDelaySeconds = ChronoUnit.SECONDS.between(dateTime, dateTime.toLocalDate().atStartOfDay().plusDays(1));
            periodicitySeconds = 60*60*24*7;
        } else if (periodicity == Periodicity.HOUR) {
            LocalDateTime nextHour = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(),
                    dateTime.getHour() + 1, 0, 0);
            initDelaySeconds = ChronoUnit.SECONDS.between(dateTime, nextHour);
            periodicitySeconds = 60*60;
        }

        return PeriodicityDto.builder()
                .initDelaySeconds(initDelaySeconds)
                .periodicitySeconds(periodicitySeconds)
                .build();

    }

}
