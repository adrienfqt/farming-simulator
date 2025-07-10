package com.example.farming.service;

import com.example.farming.entity.Stockage;
import com.example.farming.repository.StockageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockageServiceImpl implements StockageService {

    private final StockageRepository stockageRepository;

    public Optional<Stockage> getStockageEau() {
        return stockageRepository.findByEau(true);
    }
}
