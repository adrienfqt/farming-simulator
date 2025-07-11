package com.example.farming.service;

import com.example.farming.entity.TypeStockage;
import java.util.Map;

public interface StorageService {
    void addProduct(String product, int quantity, TypeStockage type);
    void removeProduct(String product, int quantity, TypeStockage type);
    boolean isFull(TypeStockage type);
    int getAvailableSpace(TypeStockage type);
    Map<String, Integer> getContents(TypeStockage type);
    void clearProduct(String product, TypeStockage type);
}
