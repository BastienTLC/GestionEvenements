package com.example.gestionevenement.controller;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.dto.LieuDto;
import com.example.gestionevenement.dto.MembreDto;
import com.example.gestionevenement.entity.Evenement;
import com.example.gestionevenement.entity.Inscription;
import com.example.gestionevenement.services.EvenementService;
import com.example.gestionevenement.services.InscriptionService;
import com.example.gestionevenement.services.impl.EvenementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private InscriptionService inscriptionService;

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
    public ResponseEntity<Object> createEvenement(@RequestBody EvenementDto evenement) {
        //Vérifier si l'événement est complet
        if (isEvenementComplet(evenement.getId())) {
            return new ResponseEntity<>("L'événement est complet", HttpStatus.INSUFFICIENT_STORAGE);
        }
        //Vérifier si l'événement chevauche un autre événement
        if (isEvenementChevauche(evenement)) {
            return new ResponseEntity<>("L'événement chevauche un autre événement", HttpStatus.BAD_REQUEST);
        }
        EvenementDto savedEvenement = evenementService.saveOrUpdateEvenement(evenement);
        return new ResponseEntity<>(savedEvenement, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour un événement existant
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvenement(@PathVariable("id") Long id, @RequestBody EvenementDto evenement) {
        EvenementDto existingEvenement = evenementService.getEvenementById(id);
        if (existingEvenement != null) {
            evenement.setId(id); // Assurez-vous que l'ID est correctement défini
            //Vérifier si l'événement est complet
            if (isEvenementComplet(id)) {
                return new ResponseEntity<>("L'événement est complet", HttpStatus.INSUFFICIENT_STORAGE);
            }
            //Vérifier si l'événement chevauche un autre événement
            if (isEvenementChevauche(evenement)) {
                return new ResponseEntity<>("L'événement chevauche un autre événement", HttpStatus.BAD_REQUEST);
            }
            EvenementDto updatedEvenement = evenementService.saveOrUpdateEvenement(evenement);
            return new ResponseEntity<>(updatedEvenement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("L'événement n'a pas été trouvé", HttpStatus.NOT_FOUND);
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

    // Endpoint pour récupérer les participants d'un événement
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<MembreDto>> getParticipantsOfEvenement(@PathVariable("id") Long id) {
        List<MembreDto> participants = inscriptionService.getParticipantsOfEvenement(id);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    //Endpoint pour récupérer le lieu d'un événement

    @GetMapping("/{id}/lieu")
    public ResponseEntity<LieuDto> getLieuByEvenementId(@PathVariable("id") Long id) {
        LieuDto lieu = evenementService.getLieuByEvenementId(id);
        return new ResponseEntity<>(lieu, HttpStatus.OK);
    }

    //Endpoint pour récupérer le nombre de participants d'un événement
    @GetMapping("/{id}/participants/count")
    public ResponseEntity<Integer> getNombreParticipantsOfEvenement(@PathVariable("id") Long id) {
        List<MembreDto> participants = inscriptionService.getParticipantsOfEvenement(id);
        return new ResponseEntity<>(participants.size(), HttpStatus.OK);
    }

    //Endpoint pour vérifier si un évènement peut être créé sinon retourner un message d'erreur
    @PostMapping("/check")
    public ResponseEntity<Object> checkEvenement(@RequestBody EvenementDto evenement) {
        //Vérifier si l'événement est complet
        if (isEvenementComplet(evenement.getId())) {
            return new ResponseEntity<>("L'événement est complet", HttpStatus.INSUFFICIENT_STORAGE);
        }
        //Vérifier si l'événement chevauche un autre événement
        if (isEvenementChevauche(evenement)) {
            return new ResponseEntity<>("L'événement chevauche un autre événement", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //fonction qui retourne true si un évènement est complet
    public boolean isEvenementComplet(Long id) {
        EvenementDto evenement = evenementService.getEvenementById(id);
        LieuDto lieu = evenementService.getLieuByEvenementId(id);
        return Objects.equals(evenement.getNombreMaxPersonne(), lieu.getCapacite_accueil());
    }

    //fonction qui retourne true si un membre est inscrit à un évènement
    public boolean isMembreInscrit(Long idMembre, Long idEvenement) {
        List<MembreDto> participants = inscriptionService.getParticipantsOfEvenement(idEvenement);
        /*for (MembreDto participant : participants) {
            if (participant.getId().equals(idMembre)) {
                return true;
            }
        }*/
        //alternative avec stream
        return participants.stream().anyMatch(participant -> participant.getId().equals(idMembre));
    }

    //fonction qui retourne true si un evenement en cours de création chevauche un autre evenement
    public boolean isEvenementChevauche(EvenementDto evenement) {

        List<EvenementDto> evenements = evenementService.getAllEvenements();


        for (EvenementDto e : evenements) {
            Date dateDebutEvenement1 = e.getDate();
            Date dateFinEvenement1 = new Date(dateDebutEvenement1.getTime() + e.getDuree().getTime());

            Date dateDebutEvenement2 = evenement.getDate();
            Date dateFinEvenement2 = new Date(dateDebutEvenement2.getTime() + evenement.getDuree().getTime());

            if(e.getIdLieu() == evenement.getIdLieu()) {
                if ((dateDebutEvenement1.compareTo(dateDebutEvenement2) <= 0 && dateDebutEvenement1.compareTo(dateFinEvenement2) >= 0) ||
                        (dateFinEvenement1.compareTo(dateDebutEvenement2) >= 0 && dateFinEvenement1.compareTo(dateFinEvenement2) <= 0) ||
                        (dateDebutEvenement1.compareTo(dateDebutEvenement2) <= 0 && dateFinEvenement1.compareTo(dateFinEvenement2) >= 0)) {
                    return true;
                }
            }
        }
        return false;
    }

}
