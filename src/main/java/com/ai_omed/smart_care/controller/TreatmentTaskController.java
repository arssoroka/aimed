package com.ai_omed.smart_care.controller;

import com.ai_omed.smart_care.entity.TreatmentTaskEntity;
import com.ai_omed.smart_care.service.TreatmentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("task")
public class TreatmentTaskController {
    @Autowired
    private TreatmentTaskService treatmentTaskService;

    @GetMapping("create")
    public List<TreatmentTaskEntity> createFromPlan(@RequestParam(name = "localDateTime", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime){

        if(localDateTime == null){
            localDateTime = LocalDateTime.now();
        }

        return treatmentTaskService.createFromPlan2(localDateTime);
    }

    @GetMapping("createOnStartDate")
    public List<TreatmentTaskEntity> createFromPlanOnStartDate(){

        return treatmentTaskService.createFromPlan2(LocalDateTime.now().with(LocalDateTime.MIN));
    }
}
