package com.example.farming.controller;

import com.example.farming.entity.Champ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/champ")
public class ChampController {

    @GetMapping("/semer/{idCulture}")
    public ResponseEntity<Champ> semerChamp(@PathVariable String idCulture) {
        Champ champ = new Champ();
        return new ResponseEntity<>(champ, HttpStatus.OK);
    }

}
