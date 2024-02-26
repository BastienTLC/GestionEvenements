package com.example.gestionlieu.services;

import com.example.gestionlieu.dto.LieuDto;
import com.example.gestionlieu.entity.Lieu;

import java.util.List;

public interface LieuService {
    List<LieuDto> getAllLieux();

    LieuDto getLieuById(Long id);

    LieuDto saveOrUpdateLieu(Lieu lieu);

    void deleteLieuById(Long id);
}
