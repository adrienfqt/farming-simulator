package com.example.farming.service;

import com.example.farming.entity.*;
import com.example.farming.repository.ChampRepository;
import com.example.farming.repository.LotRepository;
import com.example.farming.repository.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final ChampRepository champRepository;
    private final LotRepository lotRepository;
    private final CultureRepository cultureRepository;
    private final MachineService machineService;
    private final StorageService storageService;
    // ... reste du code inchangé ...
} 