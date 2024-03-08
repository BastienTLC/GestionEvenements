package com.example.gestionmembre.controller;

import com.example.gestionmembre.dto.MembreDto;
import com.example.gestionmembre.entity.Membre;
import com.example.gestionmembre.services.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/membres")
public class MembreController {
    @Autowired
    private MembreService membreService;
    private final RestTemplate restTemplate;

    public MembreController(MembreService membreService, RestTemplate restTemplate) {
        this.membreService = membreService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<List<MembreDto>> getAllMembres() {
        List<MembreDto> membres = membreService.getAllMembres();
        return new ResponseEntity<>(membres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembreDto> getMembreById(@PathVariable("id") Long id) {
        Optional<MembreDto> membre = Optional.ofNullable(membreService.getMembreById(id));
        return membre.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<MembreDto> createMembre(@RequestBody Membre membre) {
        MembreDto savedMembre = membreService.saveOrUpdateMembre(membre);
        return new ResponseEntity<>(savedMembre, HttpStatus.CREATED);
    }
    	
    @PostMapping("/new")
    public ResponseEntity<MembreDto> newMembre(@RequestBody Membre membre) {
        MembreDto savedMembre = membreService.saveOrUpdateMembre(membre);
        System.out.println(savedMembre.getId());
        String url = "http://localhost:3300/utilisateurs/signup";
        String body = "{ \"nom_utilisateur\": \""+ savedMembre.getNom() +"\", \"mot_de_passe\": \"testspringboot\", \"membre_id\": "+savedMembre.getId()+"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return new ResponseEntity<>(savedMembre, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembreDto> updateMembre(@PathVariable("id") Long id, @RequestBody Membre membre) {
        MembreDto existingMembre = membreService.getMembreById(id);
        if (existingMembre != null) {
            membre.setId(id);
            MembreDto updatedMembre = membreService.saveOrUpdateMembre(membre);
            return new ResponseEntity<>(updatedMembre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable("id") Long id) {
        membreService.deleteMembreById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
