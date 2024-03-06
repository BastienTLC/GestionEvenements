package com.example.gestionlieu.services.impl;

import com.example.gestionlieu.dto.LieuDto;
import com.example.gestionlieu.entity.Lieu;
import com.example.gestionlieu.repository.LieuRepository;
import com.example.gestionlieu.services.LieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("lieuService")
public class LieuServiceImpl implements LieuService {

    @Autowired
    private LieuRepository lieuRepository;
    public LieuServiceImpl(LieuRepository lieuRepository) {
        this.lieuRepository = lieuRepository;
    }

    @Override
    public List<LieuDto> getAllLieux() {
        List<LieuDto> lieuDtos = new ArrayList<>();
        List<Lieu> lieux = lieuRepository.findAll();
        lieux.forEach(lieu -> {
            lieuDtos.add(lieuEntityToDto(lieu));
        });
        return lieuDtos;
    }

    @Override
    public LieuDto getLieuById(Long id) {
        Lieu lieu = lieuRepository.findById(id).orElseThrow(() -> new RuntimeException("Lieu not found"));
        return lieuEntityToDto(lieu);
    }

    @Override
    public LieuDto saveOrUpdateLieu(Lieu lieu) {
        return lieuEntityToDto(lieuRepository.save(lieu));
    }

    @Override
    public void deleteLieuById(Long id) {
        String evenementUrl = "http://localhost:8082/evenements/lieux/" + id;
        RestTemplate restTemplate = new RestTemplate();
        try {
            LieuDto[] lieuxArray = restTemplate.getForObject(evenementUrl, LieuDto[].class);
            if (lieuxArray != null) {
                for (LieuDto lieu : lieuxArray) {
                    restTemplate.delete("http://localhost:8082/evenements/" + lieu.getId());
                }
            } else {
                System.out.println("No evenement found");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching evenements");
            e.printStackTrace();
        }
        lieuRepository.deleteById(id);
    }


    /*
    * Map Lieu dto to Lieu entity
    */

    private LieuDto lieuEntityToDto(Lieu lieu) {
        LieuDto lieuDto = new LieuDto();
        lieuDto.setId(lieu.getId());
        lieuDto.setNom(lieu.getNom());
        lieuDto.setAdresse(lieu.getAdresse());
        lieuDto.setCapacite_accueil(lieu.getCapacite_accueil());
        return lieuDto;
    }

    /*
    * Map Lieu entity to Lieu dto
     */

    private Lieu lieuDtoToEntity(LieuDto lieuDto) {
        Lieu lieu = new Lieu();
        lieu.setId(lieuDto.getId());
        lieu.setNom(lieuDto.getNom());
        lieu.setAdresse(lieuDto.getAdresse());
        lieu.setCapacite_accueil(lieuDto.getCapacite_accueil());
        return lieu;
    }
}
