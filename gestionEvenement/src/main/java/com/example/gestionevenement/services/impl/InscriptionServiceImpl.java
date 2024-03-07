package com.example.gestionevenement.services.impl;

import com.example.gestionevenement.dto.MembreDto;
import com.example.gestionevenement.entity.Inscription;
import com.example.gestionevenement.repository.InscriptionRepository;
import com.example.gestionevenement.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("inscriptionService")
public class InscriptionServiceImpl implements InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Override
    public List<MembreDto> getParticipantsOfEvenement(Long idEvenement) {
        List<Inscription> inscriptions = inscriptionRepository.findByIdEvenement(idEvenement);
        List<MembreDto> participants = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        for (Inscription inscription : inscriptions) {
            // Récupérer l'ID du membre de l'inscription
            Long membreId = inscription.getMembreId();
            // Construire l'URL de l'API membre avec l'ID du membre
            String membreUrl = "http://localhost:8081/membres/" + membreId;
            // Faire une requête GET à l'API membre
            MembreDto membre = restTemplate.getForObject(membreUrl, MembreDto.class);
            if (membre != null) {
                // Ajouter le membre à la liste des participants
                participants.add(membre);
            }
        }
        return participants;
    }

    @Override
    public void addParticipantToEvenement(Long idEvenement, Long idMembre) {
        Inscription inscription = new Inscription();
        inscription.setEvenementId(idEvenement);
        inscription.setMembreId(idMembre);
        inscriptionRepository.save(inscription);
    }

    @Override
    public void deleteInscriptionByEvenementId(Long id) {
        inscriptionRepository.deleteByEvenementId(id);
    }
}
