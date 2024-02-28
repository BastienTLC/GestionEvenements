package com.example.gestionevenement.repository;

import com.example.gestionevenement.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long>{
    List<Inscription> findByIdEvenement(Long idEvenement);
}
