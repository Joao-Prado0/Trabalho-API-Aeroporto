package com.exemplo.app.dto;

import java.math.BigDecimal;

public record AeroportoResponseDTO(
        Integer idAeroporto,
        String nomeAeroporto,
        String codigoIATA,
        String cidade,
        String codigoISO,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal altitude
) {
}
