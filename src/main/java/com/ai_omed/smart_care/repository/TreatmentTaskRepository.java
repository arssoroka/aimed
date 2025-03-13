package com.ai_omed.smart_care.repository;

import com.ai_omed.smart_care.entity.TreatmentTaskEntity;
import com.ai_omed.smart_care.service.dto.TreatmentTaskDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TreatmentTaskRepository extends JpaRepository<TreatmentTaskEntity, Long> {

    @Query(
            value = "SELECT TREATMENT_ACTION as treatmentAction, " +
                    "SUBJECT_PATIENT as subjectPatient, " +
                    "START_TIME as startTime, " +
                    "'ACTIVE' as STATUS, " +
                    "NULL as recurrenceStartTime  " +
                    "FROM TREATMENT_PLAN " +
                    "WHERE START_TIME <= :currentDateTime AND (END_TIME IS NULL OR  END_TIME >= :currentDateTime) " +
                    "AND RECURRENCE_PERIODICITY IS NULL " +
                    "UNION ALL "
                    +

                    "SELECT TREATMENT_ACTION as treatmentAction, " +
                    "SUBJECT_PATIENT as subjectPatient, " +
                    "START_TIME as startTime, " +
                    "'COMPLETED' as STATUS, " +
                    "NULL as recurrenceStartTime " +
                    "FROM TREATMENT_PLAN " +
                    "WHERE START_TIME <= :currentDateTime AND END_TIME < :currentDateTime " +
                    "AND RECURRENCE_PERIODICITY IS NULL " +
                    "UNION ALL " +

                    "SELECT TREATMENT_ACTION as treatmentAction, " +
                    "SUBJECT_PATIENT as subjectPatient, " +
                    "START_TIME as startTime, " +
                    "'ACTIVE' as STATUS, " +
                    "RECURRENCE_START_TIME as recurrenceStartTime " +
                    "FROM TREATMENT_PLAN " +
                    "WHERE RECURRENCE_PERIODICITY = 0 AND RECURRENCE_START_TIME <= :currentTime AND :currentTime <= RECURRENCE_END_TIME " +
                    "UNION ALL " +

                    "SELECT TREATMENT_ACTION as treatmentAction, " +
                    "SUBJECT_PATIENT as subjectPatient, " +
                    "START_TIME as startTime, " +
                    "'ACTIVE' as STATUS, " +
                    "recurrence_start_time as recurrenceStartTime " +
                    "FROM TREATMENT_PLAN " +
                    "WHERE RECURRENCE_PERIODICITY = :dayOfWeek AND RECURRENCE_START_TIME <= :currentTime AND :currentTime <= RECURRENCE_END_TIME "
            ,
            nativeQuery = true)
    List<TreatmentTaskDto> findTasksWithStatuses(@Param("currentDateTime") LocalDateTime currentDateTime,
                                                 @Param("dayOfWeek") Integer dayOfWeek,
                                                 @Param("currentTime") LocalTime currentTime
    );

}
