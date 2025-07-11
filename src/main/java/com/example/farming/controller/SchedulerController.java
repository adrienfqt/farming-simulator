package com.example.farming.controller;

import com.example.farming.service.SchedulerService;
import com.example.farming.service.SchedulerService.FieldTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {
    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/field-task")
    public ResponseEntity<Void> scheduleFieldTask(@RequestParam UUID fieldId, @RequestParam FieldTaskType taskType, @RequestParam LocalDateTime when) {
        schedulerService.scheduleFieldTask(fieldId, taskType, when);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/factory-task")
    public ResponseEntity<Void> scheduleFactoryTask(@RequestParam UUID factoryId, @RequestParam LocalDateTime when) {
        schedulerService.scheduleFactoryTask(factoryId, when);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/animal-farm-task")
    public ResponseEntity<Void> scheduleAnimalFarmTask(@RequestParam UUID farmId, @RequestParam LocalDateTime when) {
        schedulerService.scheduleAnimalFarmTask(farmId, when);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/greenhouse-task")
    public ResponseEntity<Void> scheduleGreenhouseTask(@RequestParam UUID greenhouseId, @RequestParam LocalDateTime when) {
        schedulerService.scheduleGreenhouseTask(greenhouseId, when);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/run")
    public ResponseEntity<Void> runScheduledTasks() {
        schedulerService.runScheduledTasks();
        return ResponseEntity.ok().build();
    }
} 