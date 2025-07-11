package com.example.farming.service;

import com.example.farming.entity.TypeCulture;
import java.util.UUID;

public interface FieldService {
    void plow(UUID fieldId);
    void sow(UUID fieldId, TypeCulture cropType);
    void fertilize(UUID fieldId);
    void harvest(UUID fieldId);
    void assignLot(UUID fieldId, UUID lotId);
    void updateFieldStates();
}
