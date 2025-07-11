package com.example.farming.service;

import com.example.farming.entity.Machine;
import com.example.farming.entity.TypeMachine;
import com.example.farming.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    @Override
    public boolean isAvailable(TypeMachine type, String specialization) {
        return machineRepository.findAll().stream()
                .filter(m -> m.getTypeMachine() == type)
                .filter(m -> specialization == null || specialization.equalsIgnoreCase(m.getTypeMachine().getSpecialisation()))
                .anyMatch(m -> m.isDisponible() && m.getQuantite() > 0);
    }

    @Override
    @Transactional
    public void assignMachine(TypeMachine type, String specialization) {
        List<Machine> machines = machineRepository.findAll();
        for (Machine m : machines) {
            if (m.getTypeMachine() == type && (specialization == null || specialization.equalsIgnoreCase(m.getTypeMachine().getSpecialisation())) && m.isDisponible() && m.getQuantite() > 0) {
                m.setQuantite(m.getQuantite() - 1);
                if (m.getQuantite() == 0) m.setDisponible(false);
                machineRepository.save(m);
                return;
            }
        }
        throw new IllegalStateException("Aucune machine disponible pour " + type + (specialization != null ? (" (" + specialization + ")") : ""));
    }

    @Override
    @Transactional
    public void releaseMachine(TypeMachine type, String specialization) {
        List<Machine> machines = machineRepository.findAll();
        for (Machine m : machines) {
            if (m.getTypeMachine() == type && (specialization == null || specialization.equalsIgnoreCase(m.getTypeMachine().getSpecialisation()))) {
                m.setQuantite(m.getQuantite() + 1);
                m.setDisponible(true);
                machineRepository.save(m);
                return;
            }
        }
        throw new IllegalStateException("Impossible de libérer la machine pour " + type + (specialization != null ? (" (" + specialization + ")") : ""));
    }

    @Override
    public int getAvailableCount(TypeMachine type, String specialization) {
        return machineRepository.findAll().stream()
                .filter(m -> m.getTypeMachine() == type)
                .filter(m -> specialization == null || specialization.equalsIgnoreCase(m.getTypeMachine().getSpecialisation()))
                .mapToInt(Machine::getQuantite)
                .sum();
    }

    @Override
    public Optional<String> getSpecializationForMachine(TypeMachine type) {
        return machineRepository.findAll().stream()
                .filter(m -> m.getTypeMachine() == type)
                .map(m -> m.getTypeMachine().getSpecialisation())
                .findFirst();
    }
}
