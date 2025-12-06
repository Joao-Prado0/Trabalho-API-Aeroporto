package com.exemplo.app.integrationTests;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.model.Aeroporto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Test
    @DisplayName("Integração: Deve retornar corretamente um aeroporto buscado pelo seu código IATA")
    void buscarAeroporto_ComSucesso() throws Exception {

        Aeroporto aeroporto = criarAeroportoExemplo();

        aeroportoRepository.save(aeroporto);

        mockMvc.perform(get("/api/v1/aeroportos/CNF")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.codigoIATA").value("CNF"))
                        .andExpect(jsonPath("$.nomeAeroporto").value("Aeroporto de Confins"))
                        .andExpect(jsonPath("$.cidade").value("Belo Horizonte"));
    }

    @Test
    @DisplayName("Integração: ")
    void alterarAeroporto_ComSucesso() throws Exception {
        Aeroporto aeroporto = criarAeroportoExemplo();
        aeroportoRepository.save(aeroporto);

        AeroportoRequestDTO dto =  new AeroportoRequestDTO(
                "Aeroporto Internacional Tancredo Neves",
                "CNF",
                "Belo Horizonte",
                "BR",
                -19.6244,
                -43.9719,
                800.0
        );

        mockMvc.perform(put("/api/v1/aeroportos/CNF")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeAeroporto").value("Aeroporto Internacional Tancredo Neves"));


        assertEquals(1, aeroportoRepository.count());
        assertEquals("Aeroporto Internacional Tancredo Neves", aeroportoRepository.findAll().getFirst().getNomeAeroporto());
    }


    private Aeroporto criarAeroportoExemplo(){
        Aeroporto aeroporto = new Aeroporto();
        aeroporto.setNomeAeroporto("Aeroporto de Confins");
        aeroporto.setCodigoIATA("CNF");
        aeroporto.setCidade("Belo Horizonte");
        aeroporto.setCodigoISO("BR");
        aeroporto.setLatitude(-19.6244);
        aeroporto.setLongitude(-43.9719);
        aeroporto.setAltitude(827.0);
        return aeroporto;
    }
}
