package com.example.gestionevenement.services;

import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.dto.LieuDto;
import com.example.gestionevenement.dto.MembreDto;
import com.example.gestionevenement.entity.Evenement;

import java.util.List;
import java.util.Optional;

public interface EvenementService {
    List<EvenementDto> getAllEvenements();

    List<EvenementDto> getEvenementsByLieuId(Long id);


    EvenementDto getEvenementById(Long id);

    EvenementDto saveOrUpdateEvenement(EvenementDto evenement);

    void deleteEvenementById(Long id);

    LieuDto getLieuByEvenementId(Long id);



}
