package com.example.gestionevenement.repository;

import com.example.gestionevenement.entity.Inscription;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<Inscription, Long>{
    List<Inscription> findByIdEvenement(Long idEvenement);

    @Transactional
    @Modifying
    @Query("DELETE FROM Inscription i WHERE i.idEvenement = :evenementId")
    void deleteByEvenementId(Long evenementId);
}
