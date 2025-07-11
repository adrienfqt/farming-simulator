package com.example.farming.service;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SchedulerService {
    void scheduleFieldTask(UUID fieldId, FieldTaskType taskType, LocalDateTime when);
    void scheduleFactoryTask(UUID factoryId, LocalDateTime when);
    void scheduleAnimalFarmTask(UUID farmId, LocalDateTime when);
    void scheduleGreenhouseTask(UUID greenhouseId, LocalDateTime when);
    void runScheduledTasks();

    enum FieldTaskType {
        PLOW, SOW, FERTILIZE, HARVEST, UPDATE_STATE
    }
} 