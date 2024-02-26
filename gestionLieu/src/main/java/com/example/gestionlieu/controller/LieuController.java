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

    @PutMapping("/{id}")
    public ResponseEntity<LieuDto> updateLieu(@PathVariable("id") Long id, @RequestBody Lieu lieu){
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

}
