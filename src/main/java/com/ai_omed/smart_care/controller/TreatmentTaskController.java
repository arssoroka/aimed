package com.ai_omed.smart_care.controller;

import com.ai_omed.smart_care.entity.TreatmentTaskEntity;
import com.ai_omed.smart_care.service.TreatmentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("task")
public class TreatmentTaskController {
    @Autowired
    private TreatmentTaskService treatmentTaskService;

    @PostMapping("alt")
    public List<TreatmentTaskEntity> createFromPlanAlt(@RequestParam(name = "localDateTime", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                                   @RequestParam(name = "useStartOfDay", required = false, defaultValue = "false")
                                                    boolean useStartOfDay){

        if(localDateTime == null){
            localDateTime = LocalDateTime.now();
        }
        if(useStartOfDay){
            localDateTime = localDateTime.toLocalDate().atStartOfDay();
        }

        return treatmentTaskService.createFromPlanAlt(localDateTime);
    }

    @PostMapping("")
    public List<TreatmentTaskEntity> createFromPlan(@RequestParam(name = "localDateTime", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                                                    @RequestParam(name = "useStartOfDay", required = false, defaultValue = "false")
                                                    boolean useStartOfDay){

        if(localDateTime == null){
            localDateTime = LocalDateTime.now();
        }
        if(useStartOfDay){
            localDateTime = localDateTime.toLocalDate().atStartOfDay();
        }

        return treatmentTaskService.createFromPlan(localDateTime);
    }


}
