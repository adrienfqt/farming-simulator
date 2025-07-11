package com.example.farming.service;

import com.example.farming.entity.Usine;
import com.example.farming.entity.TypeUsine;
import com.example.farming.entity.TypeStockage;
import com.example.farming.repository.UsineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FactoryServiceImpl implements FactoryService {
    private final UsineRepository usineRepository;
    private final StorageService storageService;
    // ... reste du code inchangé ...
} 