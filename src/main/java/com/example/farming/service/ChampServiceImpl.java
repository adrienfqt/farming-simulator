package com.example.farming.service;

import com.example.farming.entity.Champ;
import com.example.farming.entity.EtatChamp;
import com.example.farming.repository.ChampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChampServiceImpl implements ChampService {

    private final ChampRepository champRepository;

    public ResponseEntity<Champ> semerCultureChamp(String idCulture) {
        Optional<Champ> champ = champRepository.findFirstByEtat(EtatChamp.LABOURE);
        if (champ.isPresent()) {

        } else {

        }
        return null;
    }

}
