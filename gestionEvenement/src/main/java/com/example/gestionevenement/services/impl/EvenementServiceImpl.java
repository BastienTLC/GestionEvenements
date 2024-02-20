package com.example.gestionevenement.services.impl;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.entity.Evenement;
import com.example.gestionevenement.repository.EvenementRepository;
import com.example.gestionevenement.services.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service("evenementService")
public class EvenementServiceImpl implements EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    public EvenementServiceImpl(EvenementRepository evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    public EvenementDto saveEvenement(EvenementDto evenementDto) {
        Evenement evenement = evenementDtoToEntity(evenementDto);
        evenement = evenementRepository.save(evenement);
        return evenementEntityToDto(evenement);
    }

    // Méthode pour récupérer tous les événements
    public List<EvenementDto> getAllEvenements() {
        List<EvenementDto> evenementDtos = new ArrayList<>();
        List<Evenement> evenements = evenementRepository.findAll();
        evenements.forEach(evenement -> {
            evenementDtos.add(evenementEntityToDto(evenement));
        });
        return evenementDtos;
    }

    // Méthode pour récupérer un événement par son identifiant
    public EvenementDto getEvenementById(Long id) {

       Evenement evenement = evenementRepository.findById(id).orElseThrow(() -> new RuntimeException("Evenement not found"));
        return evenementEntityToDto(evenement);
    }

    // Méthode pour créer ou mettre à jour un événement
    public EvenementDto saveOrUpdateEvenement(Evenement evenement) {

        return evenementEntityToDto(evenementRepository.save(evenement));
    }

    // Méthode pour supprimer un événement par son identifiant
    public void deleteEvenementById(Long id) {
        evenementRepository.deleteById(id);
    }

    /**
     * Map Evenement dto to Evenement entity
     */

    private EvenementDto evenementEntityToDto(Evenement evenement) {
        EvenementDto evenementDto = new EvenementDto();
        evenementDto.setId(evenement.getId());
        evenementDto.setNom(evenement.getNom());
        evenementDto.setDate(evenement.getDate());
        evenementDto.setHeure(evenement.getHeure());
        evenementDto.setDuree(evenement.getDuree());
        //evenementDto.setIdLieu(evenement.getLieu().getId());
        evenementDto.setNombreMaxPersonne(evenement.getNombreMaxPersonne());
        return evenementDto;
    }

    /**
     * Map Evenement entity to Evenement dto
     */

    private Evenement evenementDtoToEntity(EvenementDto evenementDto) {
        Evenement evenement = new Evenement();
        evenement.setId(evenementDto.getId());
        evenement.setNom(evenementDto.getNom());
        evenement.setDate(evenementDto.getDate());
        evenement.setHeure(evenementDto.getHeure());
        evenement.setDuree(evenementDto.getDuree());
        //evenement.setLieu(evenementDto.getIdLieu());
        evenement.setNombreMaxPersonne(evenementDto.getNombreMaxPersonne());
        return evenement;
    }
}