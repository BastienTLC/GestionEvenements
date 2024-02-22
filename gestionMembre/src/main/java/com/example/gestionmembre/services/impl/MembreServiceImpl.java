package com.example.gestionmembre.services.impl;

import com.example.gestionmembre.dto.MembreDto;
import com.example.gestionmembre.entity.Membre;
import com.example.gestionmembre.repository.MembreRepository;
import com.example.gestionmembre.services.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("membreService")
            public class MembreServiceImpl implements MembreService {
    @Autowired
    private MembreRepository membreRepository;

    public MembreServiceImpl(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    public MembreDto saveMembre(MembreDto membreDto) {
        Membre membre = membreDtoToEntity(membreDto);
        membre = membreRepository.save(membre);
        return membreEntityToDto(membre);
    }

    @Override
    public List<MembreDto> getAllMembres() {
        List<MembreDto> membreDtos = new ArrayList<>();
        List<Membre> membres = membreRepository.findAll();
        membres.forEach(membre -> {
            membreDtos.add(membreEntityToDto(membre));
        });
        return membreDtos;
    }

    public MembreDto getMembreById(Long id) {
        Membre membre = membreRepository.findById(id).orElseThrow(() -> new RuntimeException("Membre not found"));
        return membreEntityToDto(membre);
    }

    public MembreDto saveOrUpdateMembre(Membre membre) {
        return membreEntityToDto(membreRepository.save(membre));
    }

    @Override
    public void deleteMembreById(Long id) {
        membreRepository.deleteById(id);
    }

    /**
     * Map Membre dto to Membre entity
     */

    private MembreDto membreEntityToDto(Membre membre) {
        MembreDto membreDto = new MembreDto();
        membreDto.setId(membre.getId());
        membreDto.setNom(membre.getNom());
        membreDto.setPrenom(membre.getPrenom());
        membreDto.setAdresse(membre.getAdresse());
        membreDto.setAge(membre.getAge());
        membreDto.setMdp(membre.getMdp());
        return membreDto;
    }

    /**
     * Map Membre entity to Membre dto
     */

    private Membre membreDtoToEntity(MembreDto membreDto) {
        Membre membre = new Membre();
        membre.setId(membreDto.getId());
        membre.setNom(membreDto.getNom());
        membre.setPrenom(membreDto.getPrenom());
        membre.setAdresse(membreDto.getAdresse());
        membre.setAge(membreDto.getAge());
        membre.setMdp(membreDto.getMdp());
        return membre;
    }
}
