package com.example.farming.service;

import com.example.farming.entity.TypeStockage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EconomyServiceImpl implements EconomyService {
    private final StorageService storageService;
    private double totalRevenue = 0;
    private final Map<String, Double> salesHistory = new HashMap<>();
    private final Map<String, Double> productionHistory = new HashMap<>();

    @Override
    @Transactional
    public void sellProduct(String product, int quantity) {
        storageService.removeProduct(product, quantity, TypeStockage.DEFAULT);
        double revenue = quantity; // 1L = 1 or
        totalRevenue += revenue;
        salesHistory.merge(product, revenue, Double::sum);
        recordSale(product, quantity, revenue);
    }

    @Override
    public double getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public Map<String, Double> getSalesHistory() {
        return new HashMap<>(salesHistory);
    }

    @Override
    public Map<String, Double> getProductionHistory() {
        return new HashMap<>(productionHistory);
    }

    @Override
    public void recordProduction(String product, double quantity) {
        productionHistory.merge(product, quantity, Double::sum);
    }

    @Override
    public void recordSale(String product, double quantity, double revenue) {
        salesHistory.merge(product, revenue, Double::sum);
    }
} 