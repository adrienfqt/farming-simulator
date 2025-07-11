package com.example.farming.service;

import org.springframework.scheduling.annotation.Scheduled;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final FieldService fieldService;
    private final FactoryService factoryService;
    private final AnimalFarmService animalFarmService;
    private final GreenhouseService greenhouseService;

    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();

    @Override
    public void scheduleFieldTask(UUID fieldId, FieldTaskType taskType, LocalDateTime when) {
        scheduledTasks.add(new ScheduledTask(TaskType.FIELD, fieldId, taskType, when));
    }

    @Override
    public void scheduleFactoryTask(UUID factoryId, LocalDateTime when) {
        scheduledTasks.add(new ScheduledTask(TaskType.FACTORY, factoryId, null, when));
    }

    @Override
    public void scheduleAnimalFarmTask(UUID farmId, LocalDateTime when) {
        scheduledTasks.add(new ScheduledTask(TaskType.ANIMAL_FARM, farmId, null, when));
    }

    @Override
    public void scheduleGreenhouseTask(UUID greenhouseId, LocalDateTime when) {
        scheduledTasks.add(new ScheduledTask(TaskType.GREENHOUSE, greenhouseId, null, when));
    }

    @Override
    @Scheduled(fixedRate = 10000)
    public void runScheduledTasks() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<ScheduledTask> it = scheduledTasks.iterator();
        while (it.hasNext()) {
            ScheduledTask task = it.next();
            if (!task.when.isAfter(now)) {
                switch (task.type) {
                    case FIELD -> {
                        switch (task.fieldTaskType) {
                            case PLOW -> fieldService.plow(task.targetId);
                            case SOW -> fieldService.sow(task.targetId, null);
                            case FERTILIZE -> fieldService.fertilize(task.targetId);
                            case HARVEST -> fieldService.harvest(task.targetId);
                            case UPDATE_STATE -> fieldService.updateFieldStates();
                        }
                    }
                    case FACTORY -> factoryService.processFactory(task.targetId);
                    case ANIMAL_FARM -> animalFarmService.processProduction(task.targetId);
                    case GREENHOUSE -> greenhouseService.processGreenhouse(task.targetId);
                }
                it.remove();
            }
        }
    }

    private enum TaskType { FIELD, FACTORY, ANIMAL_FARM, GREENHOUSE }
    private static class ScheduledTask {
        TaskType type;
        UUID targetId;
        FieldTaskType fieldTaskType;
        LocalDateTime when;
        ScheduledTask(TaskType type, UUID targetId, FieldTaskType fieldTaskType, LocalDateTime when) {
            this.type = type;
            this.targetId = targetId;
            this.fieldTaskType = fieldTaskType;
            this.when = when;
        }
    }
} 