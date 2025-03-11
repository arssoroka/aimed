package com.ai_omed.smart_care.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecurringPattern {
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
