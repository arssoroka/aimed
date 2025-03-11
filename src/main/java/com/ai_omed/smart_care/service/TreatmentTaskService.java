package com.ai_omed.smart_care.service;

import com.ai_omed.smart_care.entity.TreatmentPlanEntity;
import com.ai_omed.smart_care.entity.TreatmentTaskEntity;
import com.ai_omed.smart_care.repository.TreatmentPlanRepository;
import com.ai_omed.smart_care.repository.TreatmentTaskRepository;
import com.ai_omed.smart_care.service.dto.RecurringPattern;
import com.ai_omed.smart_care.service.util.DateUtil;
import com.ai_omed.smart_care.service.util.RecurringPatternParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TreatmentTaskService {
    private static final String ACTIVE = "ACTIVE";
    private static final String COMPLETED = "COMPLETED";

    private TreatmentTaskRepository repository;

    private TreatmentPlanRepository planRepository;

    public TreatmentTaskService(TreatmentTaskRepository repository, TreatmentPlanRepository planRepository) {
        this.repository = repository;
        this.planRepository = planRepository;
    }

    //Alternative faster approach
    public List<TreatmentTaskEntity> createFromPlan(LocalDateTime localDateTime) {
        List<TreatmentTaskEntity> treatmentTasks =
                repository.findTasksWithStatuses(localDateTime, localDateTime.getDayOfWeek().ordinal() + 1,
                        localDateTime.toLocalTime());

        for (TreatmentTaskEntity treatmentTask : treatmentTasks) {
            treatmentTask.setOnDateTime(localDateTime);
            if (treatmentTask.getRecurringStartTime() != null) {
                LocalDateTime startTime = DateUtil.combineDateWithTime(localDateTime.toLocalDate(),
                        treatmentTask.getRecurringStartTime());
                treatmentTask.setStartTime(startTime);
            }
        }

        repository.saveAll(treatmentTasks);
        return treatmentTasks;
    }

    public List<TreatmentTaskEntity> createFromPlan2(LocalDateTime dateTimeParam) {
        LocalTime localTime = dateTimeParam.toLocalTime();
        int dayOfWeek = dateTimeParam.getDayOfWeek().ordinal() + 1;

        List<TreatmentPlanEntity> plans = planRepository.findAll();
        List<TreatmentTaskEntity> tasks = new ArrayList<>();

        for (TreatmentPlanEntity plan : plans) {
            LocalDateTime startTime = plan.getStartTime();
            LocalDateTime endTime = plan.getEndTime();

            TreatmentTaskEntity treatmentTask = new TreatmentTaskEntity();
            treatmentTask.setTreatmentAction(plan.getTreatmentAction());
            treatmentTask.setSubjectPatient(plan.getSubjectPatient());
            treatmentTask.setStartTime(startTime);
            treatmentTask.setOnDateTime(dateTimeParam);

            if (plan.getRecurrencePattern() == null) {
                if ((dateTimeParam.isAfter(startTime) || dateTimeParam.equals(startTime)) && (endTime == null || dateTimeParam.isBefore(endTime) || dateTimeParam.equals(endTime))) {
                    treatmentTask.setStatus(ACTIVE);
                    tasks.add(treatmentTask);
                } else if (dateTimeParam.isAfter(startTime) && dateTimeParam.isAfter(endTime)) {
                    treatmentTask.setStatus(COMPLETED);
                    tasks.add(treatmentTask);
                }
            } else {
                List<RecurringPattern> periods = RecurringPatternParser.parse(plan.getRecurrencePattern());
                if (periods != null) {
                    boolean isInBetween = false;
                    LocalTime startingTime = null;

                    for (RecurringPattern period : periods) {
                        isInBetween = inPeriod(period, dayOfWeek, localTime);
                        if (isInBetween) {
                            startingTime = period.getStartTime();
                            break;
                        }
                    }
                    if (isInBetween) {
                        treatmentTask.setStatus(ACTIVE);
                        treatmentTask.setStartTime(DateUtil.combineDateWithTime(dateTimeParam.toLocalDate(), startingTime));
                        tasks.add(treatmentTask);

                    }
                }
            }
        }
        repository.saveAll(tasks);
        return tasks;

    }

    private boolean inPeriod(RecurringPattern recurringPattern, int dayOfWeek, LocalTime localTime) {
        if (recurringPattern.getDayOfWeek() > 0 && dayOfWeek != recurringPattern.getDayOfWeek()) {
            return false;
        }
        LocalTime startTime = recurringPattern.getStartTime();
        LocalTime endTime = recurringPattern.getEndTime();
        boolean isInBetween = (localTime.equals(startTime) || localTime.isAfter(startTime)) &&
                (localTime.equals(endTime) || localTime.isBefore(endTime));

        return isInBetween;

    }
}



