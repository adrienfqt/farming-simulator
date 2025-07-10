package com.example.farming.utils;

import com.example.farming.entity.Stockage;
import com.example.farming.repository.StockageRepository;
import com.example.farming.service.StockageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SheduledTaskService {

    private final StockageService stockageService;

    private final StockageRepository stockageRepository;

    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    public void remplirEau() {
        Optional<Stockage> stockEau = this.stockageService.getStockageEau();
        if (stockEau.isPresent()) {
            stockEau.get().setQuantiteEau(20000);
            this.stockageRepository.save(stockEau.get());
        }
        log.info("Remplir Eau fini: quantité maintenant "+stockEau.get().getQuantiteEau());
    }
}
