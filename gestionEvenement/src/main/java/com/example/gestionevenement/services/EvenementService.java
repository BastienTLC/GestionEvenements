package com.example.gestionevenement.services;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.entity.Evenement;

import java.util.List;
import java.util.Optional;

public interface EvenementService {
    List<EvenementDto> getAllEvenements();

    EvenementDto getEvenementById(Long id);

    EvenementDto saveOrUpdateEvenement(Evenement evenement);

    void deleteEvenementById(Long id);
}
