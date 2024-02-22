package com.example.gestionmembre.repository;

import com.example.gestionmembre.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {
}
