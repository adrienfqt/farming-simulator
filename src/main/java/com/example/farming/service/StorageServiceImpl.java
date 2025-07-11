package com.example.farming.service;

import com.example.farming.entity.Stockage;
import com.example.farming.entity.TypeStockage;
import com.example.farming.repository.StockageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StockageRepository stockageRepository;
} 