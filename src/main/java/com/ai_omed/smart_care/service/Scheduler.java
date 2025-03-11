package com.ai_omed.smart_care.service;

import com.ai_omed.smart_care.service.dto.Periodicity;
import com.ai_omed.smart_care.service.dto.PeriodicityDto;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(name = "app.feature.useScheduler")
public class Scheduler implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    private ScheduledExecutorService scheduledExecutorService;
    private Runnable command;

    @Value("${app.periodicity}")
    private String valueFromFile;

    private TreatmentTaskService treatmentTaskService;

    public Scheduler(TreatmentTaskService treatmentTaskService){
        this.treatmentTaskService = treatmentTaskService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Periodicity periodicity = getPeriodicity(valueFromFile);
        if(periodicity != null){
            scheduledExecutorService = Executors.newScheduledThreadPool(5);
            command = () -> {
                treatmentTaskService.createFromPlan2(LocalDateTime.now());

            };
        }

        PeriodicityDto periodicityDto = PeriodicityDto.calculateValuesForScheluler(periodicity, LocalDateTime.now());
        scheduledExecutorService.scheduleAtFixedRate(command, periodicityDto.getInitDelaySeconds(),
                periodicityDto.getPeriodicitySeconds(), TimeUnit.SECONDS);
    }


    @Override
    public void destroy() throws Exception {
        scheduledExecutorService.awaitTermination(100, TimeUnit.SECONDS);
    }

    private Periodicity getPeriodicity(String value){
        boolean existValue = Stream.of(Periodicity.values())
                .map(el -> el.name().toLowerCase()).anyMatch(el -> el.equals(value));

        return existValue ? Periodicity.valueOf(value.toUpperCase()) : null;

    }

}
