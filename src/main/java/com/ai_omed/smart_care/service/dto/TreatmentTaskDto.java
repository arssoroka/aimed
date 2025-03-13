package com.ai_omed.smart_care.service.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface TreatmentTaskDto {
    String getTreatmentAction();
    String getSubjectPatient();
    LocalDateTime getStartTime();
    String getStatus();
    LocalTime getRecurrenceStartTime();

}
