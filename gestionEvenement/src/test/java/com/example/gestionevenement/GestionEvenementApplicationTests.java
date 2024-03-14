package com.example.gestionevenement;

import com.example.gestionevenement.controller.EvenementController;
import com.example.gestionevenement.dto.EvenementDto;
import com.example.gestionevenement.services.EvenementService;
import com.example.gestionevenement.services.InscriptionService;
import com.example.gestionevenement.services.impl.EvenementServiceImpl;
import com.example.gestionevenement.services.impl.InscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GestionEvenementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvenementServiceImpl evenementService;

    @MockBean
    private InscriptionServiceImpl inscriptionService;

    private EvenementController evenementController;

    @BeforeEach
    public void setup() {
        evenementController = new EvenementController(evenementService, inscriptionService);
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void testGetAllEvenements() throws Exception {
        List<EvenementDto> evenements = new ArrayList<>();
        evenements.add(new EvenementDto(1L, "Evenement 1", new Date(), LocalTime.now(), 120L, 1L, 100));
        evenements.add(new EvenementDto(2L, "Evenement 2", new Date(), LocalTime.now(), 120L, 2L, 100));

        when(evenementService.getAllEvenements()).thenReturn(evenements);

        mockMvc.perform(MockMvcRequestBuilders.get("/evenements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

}
