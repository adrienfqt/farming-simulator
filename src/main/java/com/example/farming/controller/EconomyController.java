package com.example.farming.controller;

import com.example.farming.service.EconomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/economy")
public class EconomyController {
    private final EconomyService economyService;

    @Autowired
    public EconomyController(EconomyService economyService) {
        this.economyService = economyService;
    }

    @PostMapping("/sell")
    public ResponseEntity<Void> sellProduct(@RequestParam String product, @RequestParam int quantity) {
        economyService.sellProduct(product, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(economyService.getTotalRevenue());
    }

    @GetMapping("/sales-history")
    public ResponseEntity<Map<String, Double>> getSalesHistory() {
        return ResponseEntity.ok(economyService.getSalesHistory());
    }

    @GetMapping("/production-history")
    public ResponseEntity<Map<String, Double>> getProductionHistory() {
        return ResponseEntity.ok(economyService.getProductionHistory());
    }
} 