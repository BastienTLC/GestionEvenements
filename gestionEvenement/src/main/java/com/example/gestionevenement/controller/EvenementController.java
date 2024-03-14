package com.example.gestionevenement.controller;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.dto.LieuDto;
import com.example.gestionevenement.dto.MembreDto;
import com.example.gestionevenement.entity.Evenement;
import com.example.gestionevenement.entity.Inscription;
import com.example.gestionevenement.services.EvenementService;
import com.example.gestionevenement.services.InscriptionService;
import com.example.gestionevenement.services.impl.EvenementServiceImpl;
import com.example.gestionevenement.services.impl.InscriptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:80")
@RestController
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private InscriptionService inscriptionService;

    public EvenementController(EvenementServiceImpl evenementService, InscriptionServiceImpl inscriptionService) {
        this.evenementService = evenementService;
        this.inscriptionService = inscriptionService;
    }

    /**
     * Récupère la liste de tous les événements.
     *
     * @return une liste contenant les détails de tous les événements.
     * @response status 200 - La liste des événements a été récupérée avec succès.
     */
    @GetMapping
    public ResponseEntity<List<EvenementDto>> getAllEvenements() {
        List<EvenementDto> evenements = evenementService.getAllEvenements();
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }

    /**
     * Récupère la liste des événements par lieu.
     *
     * @param id l'identifiant du lieu.
     * @return une liste contenant les détails de tous les événements ayant lieu dans le lieu spécifié.
     * @response status 200 - La liste des événements a été récupérée avec succès.
     */
    @GetMapping("/lieux/{id}")
    public ResponseEntity<List<EvenementDto>> getEvenementsByLieuId(@PathVariable("id") Long id) {
        List<EvenementDto> evenements = evenementService.getEvenementsByLieuId(id);
        return new ResponseEntity<>(evenements, HttpStatus.OK);
    }

    /**
     * Récupère les détails d'un événement à partir de son identifiant.
     *
     * @param id l'identifiant de l'événement.
     * @return les détails de l'événement.
     * @response status 200 - Les détails de l'événement ont été récupérés avec succès.
     * @response status 404 - L'événement n'a pas été trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EvenementDto> getEvenementById(@PathVariable("id") Long id) {
        Optional<EvenementDto> evenement = Optional.ofNullable(evenementService.getEvenementById(id));
        return evenement.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Crée un nouvel événement.
     *
     * @param evenement les détails de l'événement à créer.
     * @return les détails de l'événement créé.
     * @response status 201 - L'événement a été créé avec succès.
     * @response status 400 - L'événement chevauche un autre événement.
     * @response status 507 - L'événement est complet.
     */
    @PostMapping
    public ResponseEntity<Object> createEvenement(@RequestBody EvenementDto evenement) {
        //Vérifier si l'événement chevauche un autre événement
        if (isEvenementChevauche(evenement)) {
            return new ResponseEntity<>("L'événement chevauche un autre événement", HttpStatus.BAD_REQUEST);
        }
        //Vérifier si l'événement est complet
        if (isEvenementComplet(evenement.getId())) {
            return new ResponseEntity<>("L'événement est complet", HttpStatus.INSUFFICIENT_STORAGE);
        }
        EvenementDto savedEvenement = evenementService.saveOrUpdateEvenement(evenement);
        return new ResponseEntity<>(savedEvenement, HttpStatus.CREATED);
    }

    /**
     * Ajoute un participant à un événement.
     *
     * @param idEvenement l'identifiant de l'événement.
     * @param idMembre    l'identifiant du membre.
     * @response status 201 - Le participant a été ajouté à l'événement avec succès.
     * @response status 400 - Le membre est déjà inscrit à l'événement.
     * @response status 507 - L'événement est complet.
     */
    @PostMapping("/{idEvenement}/participants/{idMembre}")
    public ResponseEntity<Object> addParticipantToEvenement(@PathVariable("idEvenement") Long idEvenement, @PathVariable("idMembre") Long idMembre) {
        //Vérifier si l'événement est complet
        if (isEvenementComplet(idEvenement)) {
            return new ResponseEntity<>("L'événement est complet", HttpStatus.INSUFFICIENT_STORAGE);
        }
        //Vérifier si le membre est déjà inscrit à l'événement
        if (isMembreInscrit(idMembre, idEvenement)) {
            return new ResponseEntity<>("Le membre est déjà inscrit à l'événement", HttpStatus.BAD_REQUEST);
        }
        inscriptionService.addParticipantToEvenement(idEvenement, idMembre);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * Met à jour les détails d'un événement.
     *
     * @param id        l'identifiant de l'événement.
     * @param evenement les nouveaux détails de l'événement.
     * @return les détails de l'événement mis à jour.
     * @response status 200 - Les détails de l'événement ont été mis à jour avec succès.
     * @response status 400 - L'événement chevauche un autre événement.
     * @response status 507 - L'événement est complet.
     * @response status 404 - L'événement n'a pas été trouvé.
     */
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


    /**
     * Supprime un événement.
     *
     * @param id l'identifiant de l'événement.
     * @response status 204 - L'événement a été supprimé avec succès.
     * @response status 404 - L'événement n'a pas été trouvé.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable("id") Long id) {
        EvenementDto evenement = evenementService.getEvenementById(id);
        if (evenement != null) {
            inscriptionService.deleteInscriptionByEvenementId(id);
            evenementService.deleteEvenementById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Récupère la liste des participants d'un événement.
     *
     * @param id l'identifiant de l'événement.
     * @return une liste contenant les détails de tous les participants de l'événement.
     * @response status 200 - La liste des participants de l'événement a été récupérée avec succès.
     */
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<MembreDto>> getParticipantsOfEvenement(@PathVariable("id") Long id) {
        List<MembreDto> participants = inscriptionService.getParticipantsOfEvenement(id);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    /**
     * Récupère le lieu d'un événement.
     *
     * @param id l'identifiant de l'événement.
     * @return les détails du lieu de l'événement.
     * @response status 200 - Les détails du lieu de l'événement ont été récupérés avec succès.
     */
    @GetMapping("/{id}/lieu")
    public ResponseEntity<LieuDto> getLieuByEvenementId(@PathVariable("id") Long id) {
        LieuDto lieu = evenementService.getLieuByEvenementId(id);
        return new ResponseEntity<>(lieu, HttpStatus.OK);
    }

    /**
     * Récupère le nombre de participants d'un événement.
     *
     * @param id l'identifiant de l'événement.
     * @return le nombre de participants de l'événement.
     * @response status 200 - Le nombre de participants de l'événement a été récupéré avec succès.
     */
    @GetMapping("/{id}/participants/count")
    public ResponseEntity<Integer> getNombreParticipantsOfEvenement(@PathVariable("id") Long id) {
        List<MembreDto> participants = inscriptionService.getParticipantsOfEvenement(id);
        return new ResponseEntity<>(participants.size(), HttpStatus.OK);
    }


    /**
     * Vérifie si un événement est complet.
     *
     * @param id l'identifiant de l'événement.
     * @return true si l'événement est complet, sinon false.
     */
    public boolean isEvenementComplet(Long id) {
        EvenementDto evenement = evenementService.getEvenementById(id);
        LieuDto lieu = evenementService.getLieuByEvenementId(id);
        return Objects.equals(evenement.getNombreMaxPersonnes(), lieu.getCapacite_accueil());
    }

    /**
     * Vérifie si un membre est inscrit à un événement.
     *
     * @param idMembre    l'identifiant du membre.
     * @param idEvenement l'identifiant de l'événement.
     * @return true si le membre est inscrit à l'événement, sinon false.
     */
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

    /**
     * Vérifie si un événement chevauche un autre événement.
     *
     * @param evenement les détails de l'événement.
     * @return true si l'événement chevauche un autre événement, sinon false.
     */
    public boolean isEvenementChevauche(EvenementDto evenement) {
        List<EvenementDto> evenements = evenementService.getAllEvenements();

        for (EvenementDto e : evenements) {
            // Vérifier si les événements ont lieu au même endroit
            if (Objects.equals(e.getLieuId(), evenement.getLieuId())) {
                // Convertir la durée de l'événement en millisecondes
                Long dureeEvenement = evenement.getDuree();

                // Combiner la date et l'heure pour chaque événement
                Calendar DateDebutEvenementCourant = Calendar.getInstance();
                DateDebutEvenementCourant.setTime(e.getDateEvenement());
                DateDebutEvenementCourant.set(Calendar.HOUR_OF_DAY, e.getHeure().getHour());
                DateDebutEvenementCourant.set(Calendar.MINUTE, e.getHeure().getMinute());
                DateDebutEvenementCourant.set(Calendar.SECOND, e.getHeure().getSecond());
                Date dateDebutEvenement1 = DateDebutEvenementCourant.getTime();

                Calendar DateFinEvenementCourant = Calendar.getInstance();
                DateFinEvenementCourant.setTime(dateDebutEvenement1);
                DateFinEvenementCourant.add(Calendar.MILLISECOND, e.getDuree().intValue());
                Date dateFinEvenement1 = DateFinEvenementCourant.getTime();

                Calendar dateDebutEvenementCree = Calendar.getInstance();
                dateDebutEvenementCree.setTime(evenement.getDateEvenement());
                dateDebutEvenementCree.set(Calendar.HOUR_OF_DAY, evenement.getHeure().getHour());
                dateDebutEvenementCree.set(Calendar.MINUTE, evenement.getHeure().getMinute());
                dateDebutEvenementCree.set(Calendar.SECOND, evenement.getHeure().getSecond());
                Date dateDebutEvenement2 = dateDebutEvenementCree.getTime();

                Calendar dateFinEvenementCree = Calendar.getInstance();
                dateFinEvenementCree.setTime(dateDebutEvenement2);
                dateFinEvenementCree.add(Calendar.MILLISECOND, dureeEvenement.intValue());
                Date dateFinEvenement2 = dateFinEvenementCree.getTime();

                // Vérifier s'il y a un chevauchement entre les deux événements
                if ((dateDebutEvenement1.compareTo(dateFinEvenement2) <= 0 && dateFinEvenement1.compareTo(dateDebutEvenement2) >= 0) ||
                        (dateDebutEvenement2.compareTo(dateFinEvenement1) <= 0 && dateFinEvenement2.compareTo(dateDebutEvenement1) >= 0)) {
                    return true; // Il y a un chevauchement
                }
            }
        }
        return false; // Pas de chevauchement trouvé
    }


}
