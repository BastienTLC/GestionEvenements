package com.example.gestionmembre.services;

import com.example.gestionmembre.dto.MembreDto;
import com.example.gestionmembre.entity.Membre;

import java.util.List;

public interface MembreService {
    List<MembreDto> getAllMembres();

    MembreDto getMembreById(Long id);

    MembreDto saveOrUpdateMembre(Membre membre);

    void deleteMembreById(Long id);

}
