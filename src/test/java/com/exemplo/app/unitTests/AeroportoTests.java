package com.exemplo.app.unitTests;

import com.exemplo.app.model.Aeroporto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AeroportoTests {

    private final Aeroporto aeroporto = new Aeroporto();

    @Test
    @DisplayName("Deve converter corretamente Pés para Metros")
    void conversaoPesParaMetros(){
        Double altitudeEmPes = 1000.00;

        Double valorEsperado = 304.8;

        assertEquals(valorEsperado, aeroporto.converterPesParaMetros(altitudeEmPes), 0.001);
    }

}
