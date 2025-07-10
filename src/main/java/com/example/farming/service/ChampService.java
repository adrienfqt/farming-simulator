package com.example.farming.service;

import com.example.farming.entity.Champ;
import org.springframework.http.ResponseEntity;

public interface ChampService {

    public ResponseEntity<Champ> semerCultureChamp(String idCulture);
}
