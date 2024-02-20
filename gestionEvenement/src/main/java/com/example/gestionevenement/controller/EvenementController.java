package com.example.gestionevenement.controller;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.entity.Evenement;
import com.example.gestionevenement.services.EvenementService;
import com.example.gestionevenement.services.impl.EvenementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;

    public EvenementController(EvenementServiceImpl evenementService) {
        this.evenementService = evenementService;
    }

    // Endpoint pour récupérer tous les événements
    @GetMapping
    public ResponseEntity<List<EvenementDto>> getAllEvenements() {
        List<EvenementDto> evenements = evenementService.getAllEvenements();
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }

    // Endpoint pour récupérer un événement par son identifiant
    @GetMapping("/{id}")
    public ResponseEntity<EvenementDto> getEvenementById(@PathVariable("id") Long id) {
        Optional<EvenementDto> evenement = Optional.ofNullable(evenementService.getEvenementById(id));
        return evenement.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint pour créer un nouvel événement
    @PostMapping
    public ResponseEntity<EvenementDto> createEvenement(@RequestBody Evenement evenement) {
        EvenementDto savedEvenement = evenementService.saveOrUpdateEvenement(evenement);
        return new ResponseEntity<>(savedEvenement, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour un événement existant
    @PutMapping("/{id}")
    public ResponseEntity<EvenementDto> updateEvenement(@PathVariable("id") Long id, @RequestBody Evenement evenement) {
        EvenementDto existingEvenement = evenementService.getEvenementById(id);
        if (existingEvenement != null) {
            evenement.setId(id); // Assurez-vous que l'ID est correctement défini
            EvenementDto updatedEvenement = evenementService.saveOrUpdateEvenement(evenement);
            return new ResponseEntity<>(updatedEvenement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer un événement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable("id") Long id) {
        EvenementDto evenement = evenementService.getEvenementById(id);
        if (evenement != null) {
            evenementService.deleteEvenementById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}