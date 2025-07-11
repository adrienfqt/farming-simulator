package com.example.farming.controller;

import com.example.farming.entity.TypeStockage;
import com.example.farming.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/stockages")
public class StockageController {
    private final StorageService storageService;

    @Autowired
    public StockageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{type}")
    public ResponseEntity<Map<String, Integer>> getContents(@PathVariable TypeStockage type) {
        return ResponseEntity.ok(storageService.getContents(type));
    }

    @PostMapping("/{type}/add")
    public ResponseEntity<Void> addProduct(@PathVariable TypeStockage type, @RequestParam String product, @RequestParam int quantity) {
        storageService.addProduct(product, quantity, type);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{type}/remove")
    public ResponseEntity<Void> removeProduct(@PathVariable TypeStockage type, @RequestParam String product, @RequestParam int quantity) {
        storageService.removeProduct(product, quantity, type);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{type}/is-full")
    public ResponseEntity<Boolean> isFull(@PathVariable TypeStockage type) {
        return ResponseEntity.ok(storageService.isFull(type));
    }

    @GetMapping("/{type}/available-space")
    public ResponseEntity<Integer> getAvailableSpace(@PathVariable TypeStockage type) {
        return ResponseEntity.ok(storageService.getAvailableSpace(type));
    }

    @DeleteMapping("/{type}/clear")
    public ResponseEntity<Void> clearProduct(@PathVariable TypeStockage type, @RequestParam String product) {
        storageService.clearProduct(product, type);
        return ResponseEntity.ok().build();
    }
} 