package com.exemplo.app.unitTests;

import com.exemplo.app.utils.CountryUtils; // Certifique-se de importar sua classe utilitária
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CountryUtilsTests {

    @Test
    @DisplayName("Deve retornar 'BR' ao passar 'Brazil' (Requisito do PDF)")
    void deveRetornarIsoBrasil() {
        String resultado = CountryUtils.getIsoCode("Brazil");

        assertEquals("BR", resultado);
    }

    @Test
    @DisplayName("Deve funcionar independente de maiúsculas ou minúsculas")
    void deveRetornarIsoCaseInsensitive() {
        assertEquals("AR", CountryUtils.getIsoCode("ARGENTINA"));
        assertEquals("AR", CountryUtils.getIsoCode("argentina"));
        assertEquals("AR", CountryUtils.getIsoCode("Argentina"));
    }

    @Test
    @DisplayName("Deve retornar nulo ou padrão para país inexistente")
    void deveRetornarNuloParaPaisDesconhecido() {
        String resultado = CountryUtils.getIsoCode("Narnia");

        assertNull(resultado);
    }
}