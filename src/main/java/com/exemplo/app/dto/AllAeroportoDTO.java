package com.exemplo.app.dto;

import java.util.ArrayList;

public record AllAeroportoDTO(
        ArrayList<AeroportoResponseDTO> listaAeroportos
) {
}
