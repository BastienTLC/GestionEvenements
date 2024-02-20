package com.example.gestionevenement.repository;

import com.example.gestionevenement.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {

}
