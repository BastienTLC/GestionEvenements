package com.example.gestionevenement.services;

import com.example.gestionevenement.dto.MembreDto;

import java.util.List;

public interface InscriptionService {
    
    List<MembreDto> getParticipantsOfEvenement(Long idEvenement);

    void deleteInscriptionByEvenementId(Long id);

}
