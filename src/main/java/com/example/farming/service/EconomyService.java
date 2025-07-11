package com.example.farming.service;

import java.util.Map;

public interface EconomyService {
    void sellProduct(String product, int quantity);
    double getTotalRevenue();
    Map<String, Double> getSalesHistory();
    Map<String, Double> getProductionHistory();
    void recordProduction(String product, double quantity);
    void recordSale(String product, double quantity, double revenue);
} 