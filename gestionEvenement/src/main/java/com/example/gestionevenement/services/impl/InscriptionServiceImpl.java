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
            Long membreId = inscription.getMembreId();
            String membreUrl = "http://membre-api:8081/" + membreId;
            MembreDto membre = restTemplate.getForObject(membreUrl, MembreDto.class);
            if (membre != null) {
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
