package com.ai_omed.smart_care.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "treatment_task")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class TreatmentTaskEntity {
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
    private LocalTime recurringStartTime;

    @Column
    private String status;

    @Column
    private LocalDateTime onDateTime;

}
