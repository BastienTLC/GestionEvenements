package com.example.gestionevenement.repository;

import com.example.gestionevenement.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    List<Evenement> findByLieuId(Long id);
}
