package com.exemplo.app.dto;

import jakarta.validation.constraints.NotNull;

public record AeroportoRequestDTO(
        @NotNull String nomeAeroporto,
        @NotNull String codigoIATA,
        @NotNull String cidade,
        @NotNull String codigoISO,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotNull Double altitude
) {
}
