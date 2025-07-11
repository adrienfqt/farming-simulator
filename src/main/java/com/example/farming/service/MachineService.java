package com.example.farming.service;

import com.example.farming.entity.TypeMachine;
import java.util.Optional;

public interface MachineService {
    boolean isAvailable(TypeMachine type, String specialization);
    void assignMachine(TypeMachine type, String specialization);
    void releaseMachine(TypeMachine type, String specialization);
    int getAvailableCount(TypeMachine type, String specialization);
    Optional<String> getSpecializationForMachine(TypeMachine type);
}
