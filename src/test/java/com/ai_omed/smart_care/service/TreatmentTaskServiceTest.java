package com.ai_omed.smart_care.service;

import com.ai_omed.smart_care.entity.TreatmentPlanEntity;
import com.ai_omed.smart_care.entity.TreatmentTaskEntity;
import com.ai_omed.smart_care.repository.TreatmentPlanRepository;
import com.ai_omed.smart_care.repository.TreatmentTaskRepository;
import com.ai_omed.smart_care.service.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TreatmentTaskServiceTest {
    public static final String ACTIVE = "ACTIVE";
    public static final String COMPLETED = "COMPLETED";

    private static final String ACTION1 = "ACTION1";
    private static final String ACTION2 = "ACTION2";
    private static final String ACTION3 = "ACTION3";
    private static final String ACTION4 = "ACTION4";


    private static final String PATIENT1 = "PATIENT1";
    private static final String PATIENT2 = "PATIENT2";

    private static final LocalDateTime DATE_TIME0 = DateUtil.parseDateTime("2025-01-01T15:00:00");
    private static final LocalDateTime DATE_TIME02 = DateUtil.parseDateTime("2025-01-01T16:00:00");

    private static final LocalDateTime DATE_TIME1 = DateUtil.parseDateTime("2025-03-01T12:00:00");
    private static final LocalDateTime DATE_TIME1_5 = DateUtil.parseDateTime("2025-03-03T11:00:00");

    private static final LocalDateTime DATE_TIME2 = DateUtil.parseDateTime("2025-03-05T12:00:00");
    private static final LocalDateTime DATE_TIME2_5 = DateUtil.parseDateTime("2025-03-06T12:00:00");

    private static final LocalDateTime DATE_TIME3 = DateUtil.parseDateTime("2025-03-10T12:00:00");
    private static final LocalDateTime DATE_TIME3_0 = DateUtil.parseDateTime("2025-03-10T09:00:00");


    private static final LocalDateTime DATE_TIME4 = DateUtil.parseDateTime("2025-03-15T12:00:00");
    private static final LocalDateTime DATE_TIME5 = DateUtil.parseDateTime("2025-03-20T12:00:00");

    @Mock
    private TreatmentPlanRepository treatmentPlanRepository;

    @Mock
    private TreatmentTaskRepository treatmentTaskRepository;

    @BeforeEach
    void init() {
        TreatmentTaskService treatmentTaskService =
                new TreatmentTaskService(treatmentTaskRepository, treatmentPlanRepository);
        when(treatmentPlanRepository.findAll()).thenReturn(createPlans());


    }

    @Test
    public void createFromPlan_it_should_return_zero_elements_because_treatment_plans_are_not_started() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME0);

        Assertions.assertEquals(0, tasks.size());
    }

    @Test
    public void createFromPlan2_it_should_return_one_element_because_recurring_plan_at_16_00_every_day() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME02);

        Assertions.assertEquals(Collections.singletonList(TreatmentTaskEntity.builder()
                .treatmentAction(ACTION3)
                .subjectPatient(PATIENT2)
                .onDateTime(DATE_TIME02)
                .status(ACTIVE)
                .startTime(DateUtil.parseDateTime("2025-01-01T16:00"))
                .build()), tasks);

    }

    @Test
    public void createFromPlan2_it_should_return_one_element_because_recurring_plan_on_Monday() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService
                .createFromPlan(DateUtil.parseDateTime("2025-02-24T08:30"));

        Assertions.assertEquals(Collections.singletonList(TreatmentTaskEntity.builder()
                .treatmentAction(ACTION4)
                .subjectPatient(PATIENT2)
                .onDateTime(DateUtil.parseDateTime("2025-02-24T08:30"))
                .status(ACTIVE)
                .startTime(DateUtil.parseDateTime("2025-02-24T08:00"))
                .build()), tasks);

    }


    @Test
    public void createFromPlan_it_should_return_one_active_unlimmited_not_recurring_task() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME1_5);

        Assertions.assertEquals(Collections.singletonList(TreatmentTaskEntity.builder()
                .treatmentAction(ACTION1)
                .subjectPatient(PATIENT1)
                .onDateTime(DATE_TIME1_5)
                .status(ACTIVE)
                .startTime(DATE_TIME1)
                .build()), tasks);

    }

    @Test
    public void createFromPlan_it_should_return_two_active_not_recurring_tasks_unlimmited_and_limited() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME2_5);

        Assertions.assertEquals(Arrays.asList(TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION1)
                        .subjectPatient(PATIENT1)
                        .status(ACTIVE)
                        .onDateTime(DATE_TIME2_5)
                        .startTime(DATE_TIME1)
                        .build(),
                TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION2)
                        .subjectPatient(PATIENT1)
                        .onDateTime(DATE_TIME2_5)
                        .startTime(DATE_TIME2)
                        .status(ACTIVE)
                        .build()
        ), tasks);

    }

    @Test
    public void createFromPlan2_it_should_return_two_active_not_recurring_tasks_unlimmited_and_limited() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME2);

        Assertions.assertEquals(Arrays.asList(TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION1)
                        .subjectPatient(PATIENT1)
                        .onDateTime(DATE_TIME2)
                        .status(ACTIVE)
                        .startTime(DATE_TIME1)
                        .build(),
                TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION2)
                        .subjectPatient(PATIENT1)
                        .onDateTime(DATE_TIME2)
                        .startTime(DATE_TIME2)
                        .status(ACTIVE)
                        .build()
        ), tasks);

    }

    @Test
    public void createFromPlan_it_should_return_two_active_not_recurring_tasks_unlimmited_and_limited_and_recurring() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME3);

        Assertions.assertEquals(Arrays.asList(TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION1)
                        .subjectPatient(PATIENT1)
                        .onDateTime(DATE_TIME3)
                        .status(ACTIVE)
                        .startTime(DATE_TIME1)
                        .build(),
                TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION2)
                        .onDateTime(DATE_TIME3)
                        .subjectPatient(PATIENT1)
                        .startTime(DATE_TIME2)
                        .status(ACTIVE)
                        .build(),
                TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION4)
                        .onDateTime(DATE_TIME3)
                        .subjectPatient(PATIENT2)
                        .startTime(DATE_TIME3)
                        .status(ACTIVE)
                        .build()
        ), tasks);

    }


    @Test
    public void createFromPlan_it_should_return_two_not_recurring_tasks_active_completed_and_one_recurring_onMonday() {
        TreatmentTaskService treatmentTaskService = new TreatmentTaskService(treatmentTaskRepository,
                treatmentPlanRepository);

        List<TreatmentTaskEntity> tasks = treatmentTaskService.createFromPlan(DATE_TIME4);

        Assertions.assertEquals(Arrays.asList(TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION1)
                        .subjectPatient(PATIENT1)
                        .status(ACTIVE)
                        .onDateTime(DATE_TIME4)
                        .startTime(DATE_TIME1)
                        .build(),
                TreatmentTaskEntity.builder()
                        .treatmentAction(ACTION2)
                        .subjectPatient(PATIENT1)
                        .onDateTime(DATE_TIME4)
                        .startTime(DATE_TIME2)
                        .status(COMPLETED)
                        .build()

        ), tasks);

    }


    private List<TreatmentPlanEntity> createPlans() {
        return Arrays.asList(
                TreatmentPlanEntity.builder()
                        .treatmentAction(ACTION1)
                        .subjectPatient(PATIENT1)
                        .startTime(DATE_TIME1)
                        .build(),
                TreatmentPlanEntity.builder()
                        .treatmentAction(ACTION2)
                        .subjectPatient(PATIENT1)
                        .startTime(DATE_TIME2)
                        .endTime(DATE_TIME3)
                        .build(),
                TreatmentPlanEntity.builder()
                        .treatmentAction(ACTION3)
                        .subjectPatient(PATIENT2)
                        .recurrencePattern("every day at 08:00 and 16:00")
                        .build(),
                TreatmentPlanEntity.builder()
                        .treatmentAction(ACTION4)
                        .subjectPatient(PATIENT2)
                        .recurrencePattern("every Monday from 08:00 to 09:00 and from 12:00 to 14:00")
                        .build()

        );

    }


}