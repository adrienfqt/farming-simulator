package com.example.farming.service;

import java.util.UUID;

public interface GreenhouseService {
    void processGreenhouse(UUID greenhouseId);
    boolean canProcess(UUID greenhouseId);
    void pauseGreenhouse(UUID greenhouseId);
    void resumeGreenhouse(UUID greenhouseId);
    void processAllGreenhouses();
} 