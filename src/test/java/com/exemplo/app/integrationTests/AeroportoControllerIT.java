package com.exemplo.app.integrationTests;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.repository.AeroportoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AeroportoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AeroportoRepository aeroportoRepository;

    @BeforeEach
    void setup(){
        aeroportoRepository.deleteAll();
    }

    @Test
    @DisplayName("Integração: Deve cadastrar aeroporto e retornar 201 Created")
    void cadastrarAeroporto_ComSucesso() throws Exception{

        AeroportoRequestDTO dto = new AeroportoRequestDTO(
                "Aeroporto Int. de Teste",
                "TST",
                "Cidade Teste",
                "BR",
                -20.5,
                -40.5,
                800.0
        );

        mockMvc.perform(post("/api/v1/aeroportos/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoIATA").value("TST"))
                .andExpect(jsonPath("$.nomeAeroporto").value("Aeroporto Int. de Teste"));

        assertEquals(1, aeroportoRepository.count());
        assertEquals("TST",aeroportoRepository.findAll().getFirst().getCodigoIATA());
    }
}
