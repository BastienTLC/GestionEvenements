package com.example.gestionlieu.controller;

import com.example.gestionlieu.dto.LieuDto;
import com.example.gestionlieu.entity.Lieu;
import com.example.gestionlieu.services.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/lieux")
public class LieuController {
    @Autowired
    private LieuService lieuService;

    @GetMapping
    public ResponseEntity<List<LieuDto>> getAllLieux(){
        List<LieuDto> lieux = lieuService.getAllLieux();
        return new ResponseEntity<>(lieux, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LieuDto> getLieuById(@PathVariable("id") Long id){
        Optional<LieuDto> lieu = Optional.ofNullable(lieuService.getLieuById(id));
        return lieu.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LieuDto> createLieu(@RequestBody Lieu lieu){

        if(isLieuExist(lieu)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(!isLieuValid(lieu)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LieuDto newLieu = lieuService.saveOrUpdateLieu(lieu);
        return new ResponseEntity<>(newLieu, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LieuDto> updateLieu(@PathVariable("id") Long id, @RequestBody Lieu lieu){
        if(!isLieuValid(lieu)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LieuDto existingLieu = lieuService.getLieuById(id);
        if(existingLieu != null){
            lieu.setId(id);
            LieuDto updatedLieu = lieuService.saveOrUpdateLieu(lieu);
            return new ResponseEntity<>(updatedLieu, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLieu(@PathVariable("id") Long id){

        lieuService.deleteLieuById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //fonction pour tester si un nouveau existe déjà même adresse
    private boolean isLieuExist(Lieu lieu){
        return lieuService.getAllLieux().stream().anyMatch(l -> l.getAdresse().equals(lieu.getAdresse()));
    }
    //fonction qui verifie que tous les champs sont non null et ne contiennent pas de valeurs vides
    private boolean isLieuValid(Lieu lieu){
        return lieu.getNom() != null && !lieu.getNom().isEmpty() && lieu.getAdresse() != null && !lieu.getAdresse().isEmpty() && lieu.getCapacite_accueil() != null;
    }

}
