package com.exemplo.app.unitTests;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.exceptions.AeroportoNaoEncontradoException;
import com.exemplo.app.model.Aeroporto;
import com.exemplo.app.repository.AeroportoRepository;
import com.exemplo.app.service.AeroportoService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AeroportoServiceTests {

    @Mock
    private AeroportoRepository aeroportoRepository;

    @InjectMocks
    private AeroportoService aeroportoService;

    @Test
    @DisplayName("Deve lançar exceção quando buscar com IATA inexistente")
    void buscarPorIATAInexistente(){
        String iata = "ZZZ";
        when(aeroportoRepository.findByCodigoIATA(iata)).thenReturn(Optional.empty());

        AeroportoNaoEncontradoException exception = assertThrows(AeroportoNaoEncontradoException.class, () ->{
            aeroportoService.buscarAeroportoPorIATA(iata);
        });

        assertEquals("Nenhum aeroporto encotrado com IATA: " + iata, exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar registrar aeroporto com IATA inválidos")
    void registrarAeroportoInvalido_IATA(){
        AeroportoRequestDTO badDTO = new AeroportoRequestDTO(
                "Aeroporto",
                "Aero",
                "BH",
                "BR",
                -21.1231231,
                -19.129313,
                1200.01
                );

        when(aeroportoRepository.findByCodigoIATA("Aero")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
           aeroportoService.registrarNovoAeroporto(badDTO);
        });
        assertEquals("Código IATA inválido", exception.getMessage());

        verify(aeroportoRepository, never()).save(any(Aeroporto.class));
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar registrar aeroporto com altitude inválida")
    void registrarAeroportoInvalido_Altitude(){
        AeroportoRequestDTO badDTO = new AeroportoRequestDTO(
                "Aeroporto",
                "TST",
                "BH",
                "BR",
                -21.1231231,
                -19.129313,
                -420.21
        );
        when(aeroportoRepository.findByCodigoIATA("TST")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
            aeroportoService.registrarNovoAeroporto(badDTO);
        });
        assertEquals("Altitude Inválida", exception.getMessage());

        verify(aeroportoRepository, never()).save(any(Aeroporto.class));
    }
}
