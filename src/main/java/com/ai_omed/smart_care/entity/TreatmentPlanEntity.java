package com.ai_omed.smart_care.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "treatment_plan")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TreatmentPlanEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String treatmentAction;

  @Column
  private String subjectPatient;

  @Column
  private LocalDateTime startTime;

  @Column
  private LocalDateTime endTime;

  @Column
  private String recurrencePattern;

  @Column
  private Integer recurrencePeriodicity;

  @Column
  private LocalTime recurrenceStartTime;

  @Column
  private LocalTime recurrenceEndTime;

}
