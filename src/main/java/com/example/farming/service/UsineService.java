package com.example.farming.service;

import com.example.farming.entity.TypeUsine;
import java.util.UUID;

public interface FactoryService {
    void processFactory(UUID factoryId);
    boolean canProcess(UUID factoryId);
    void pauseFactory(UUID factoryId);
    void resumeFactory(UUID factoryId);
    void processAllFactories();
    void processBakery(UUID factoryId);
    void processChocolateFactory(UUID factoryId);
}
