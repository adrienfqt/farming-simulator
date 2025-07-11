package com.example.farming.service;

import com.example.farming.entity.*;
import com.example.farming.repository.ChampRepository;
import com.example.farming.repository.LotRepository;
import com.example.farming.repository.CultureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FieldServiceImpl implements FieldService {
    private final ChampRepository champRepository;
    private final LotRepository lotRepository;
    private final CultureRepository cultureRepository;
    private final MachineService machineService;
    private final StorageService storageService;

    @Autowired
    public FieldServiceImpl(ChampRepository champRepository,
                           LotRepository lotRepository,
                           CultureRepository cultureRepository,
                           MachineService machineService,
                           StorageService storageService) {
        this.champRepository = champRepository;
        this.lotRepository = lotRepository;
        this.cultureRepository = cultureRepository;
        this.machineService = machineService;
        this.storageService = storageService;
    }

    @Override
    @Transactional
    public void plow(UUID fieldId) {
        Champ champ = champRepository.findById(fieldId).orElseThrow();
        if (champ.getEtat() != EtatChamp.RECOLTE) throw new IllegalStateException("Champ non récolté");
        // Vérifier disponibilité machine
        machineService.assignMachine(TypeMachine.TRACTEUR, null);
        champ.setEtat(EtatChamp.LABOURE);
        champRepository.save(champ);
    }

    @Override
    @Transactional
    public void sow(UUID fieldId, TypeCulture cropType) {
        Champ champ = champRepository.findById(fieldId).orElseThrow();
        if (champ.getEtat() != EtatChamp.LABOURE) throw new IllegalStateException("Champ non labouré");
        Culture culture = cultureRepository.findByTypeCulture(cropType).orElseThrow();
        // Vérifier disponibilité machine
        machineService.assignMachine(TypeMachine.SEMEUSE, null);
        champ.setCulture(culture);
        champ.setEtat(EtatChamp.SEME);
        champ.setDebutSemis(LocalDateTime.now());
        champ.setDateRecoltePrevue(LocalDateTime.now().plusMinutes(2));
        champRepository.save(champ);
    }

    @Override
    @Transactional
    public void fertilize(UUID fieldId) {
        Champ champ = champRepository.findById(fieldId).orElseThrow();
        if (champ.getEtat() != EtatChamp.SEME) throw new IllegalStateException("Champ non semé");
        // Vérifier fertilisant en stockage
        storageService.removeProduct("fertilisant", 50, TypeStockage.DEFAULT);
        // Vérifier disponibilité machine
        machineService.assignMachine(TypeMachine.FERTILISATEUR, null);
        champ.setEtat(EtatChamp.FERTILISE);
        champ.setFertilise(true);
        champ.setDateFertilisation(LocalDateTime.now());
        champ.setRendementBoost(1.5);
        champRepository.save(champ);
    }

    @Override
    @Transactional
    public void harvest(UUID fieldId) {
        Champ champ = champRepository.findById(fieldId).orElseThrow();
        if (champ.getEtat() != EtatChamp.PRET_A_RECOLTER) throw new IllegalStateException("Champ pas prêt à récolter");
        // Vérifier disponibilité machine
        machineService.assignMachine(TypeMachine.MOISSONNEUSE, null);
        // Calcul rendement
        double rendement = champ.getCulture().getRendement();
        if (champ.isFertilise()) rendement *= champ.getRendementBoost();
        // Vérifier stockage
        storageService.addProduct(champ.getCulture().getName(), (int) rendement, TypeStockage.DEFAULT);
        champ.setEtat(EtatChamp.RECOLTE);
        champ.setDateDerniereRecolte(LocalDateTime.now());
        champ.setRendementDerniereRecolte(rendement);
        champ.setOrDerniereRecolte(rendement); // 1L = 1 or
        champ.setFertilise(false);
        champ.setRendementBoost(1.0);
        champRepository.save(champ);
    }

    @Override
    @Transactional
    public void assignLot(UUID fieldId, UUID lotId) {
        Champ champ = champRepository.findById(fieldId).orElseThrow();
        Lot lot = lotRepository.findById(lotId).orElseThrow();
        champ.setLot(lot);
        champRepository.save(champ);
    }

    @Override
    @Transactional
    public void updateFieldStates() {
        // Met à jour les champs SEME/FERTILISE en PRET_A_RECOLTER si délai écoulé
        champRepository.findAll().forEach(champ -> {
            if ((champ.getEtat() == EtatChamp.SEME || champ.getEtat() == EtatChamp.FERTILISE)
                && champ.getDateRecoltePrevue() != null
                && LocalDateTime.now().isAfter(champ.getDateRecoltePrevue())) {
                champ.setEtat(EtatChamp.PRET_A_RECOLTER);
                champRepository.save(champ);
            }
        });
    }
}
