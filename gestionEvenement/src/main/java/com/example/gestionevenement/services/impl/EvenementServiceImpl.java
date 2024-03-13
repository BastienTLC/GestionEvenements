package com.example.gestionevenement.services.impl;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.dto.LieuDto;
import com.example.gestionevenement.dto.MembreDto;
import com.example.gestionevenement.entity.Evenement;
import com.example.gestionevenement.repository.EvenementRepository;
import com.example.gestionevenement.services.EvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    // Méthode pour récupérer tous les événements par lieu
    @Override
    public List<EvenementDto> getEvenementsByLieuId(Long id) {
        List<EvenementDto> evenementDtos = new ArrayList<>();
        List<Evenement> evenements = evenementRepository.findByLieuId(id);
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
    public EvenementDto saveOrUpdateEvenement(EvenementDto evenement) {
        Evenement evenementEntity = evenementDtoToEntity(evenement);

        return evenementEntityToDto(evenementRepository.save(evenementEntity));
    }

    // Méthode pour supprimer un événement par son identifiant
    public void deleteEvenementById(Long id) {
        //supprimer les inscriptions de l'événement
        evenementRepository.deleteById(id);
    }

    @Override
    public LieuDto getLieuByEvenementId(Long id) {
        Evenement evenement = evenementRepository.findById(id).orElseThrow(() -> new RuntimeException("Evenement not found"));
        Long LieuId = evenement.getLieuId();
        String lieuUrl = "http://lieu-api:8083/" + LieuId;
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(lieuUrl, LieuDto.class);
        }
        catch (Exception e){
            throw new RuntimeException("Lieu not found", e);
        }
    }



    /**
     * Map Evenement entity to EvenementDto
     */
    private EvenementDto evenementEntityToDto(Evenement evenement) {
        EvenementDto evenementDto = new EvenementDto();
        evenementDto.setId(evenement.getId());
        evenementDto.setNom(evenement.getNom());
        evenementDto.setDateEvenement(evenement.getDateEvenement());
        evenementDto.setHeure(evenement.getHeure());
        evenementDto.setDuree(evenement.getDuree());
        evenementDto.setLieuId(evenement.getLieuId());
        evenementDto.setNombreMaxPersonnes(evenement.getNombreMaxPersonnes());
        return evenementDto;
    }

    /**
     * Map EvenementDto to Evenement entity
     */
    private Evenement evenementDtoToEntity(EvenementDto evenementDto) {
        Evenement evenement = new Evenement();
        evenement.setId(evenementDto.getId());
        evenement.setNom(evenementDto.getNom());
        evenement.setDateEvenement(evenementDto.getDateEvenement());
        evenement.setHeure(evenementDto.getHeure());
        evenement.setDuree(evenementDto.getDuree());
        evenement.setLieuId(evenementDto.getLieuId());
        evenement.setNombreMaxPersonnes(evenementDto.getNombreMaxPersonnes());
        return evenement;
    }
}
