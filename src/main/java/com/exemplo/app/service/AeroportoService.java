package com.exemplo.app.service;

import com.exemplo.app.dto.AeroportoResponseDTO;
import com.exemplo.app.dto.AllAeroportoDTO;
import com.exemplo.app.model.Aeroporto;
import com.exemplo.app.repositiory.AeroportoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AeroportoService {

    private final AeroportoRepository aeroportoRepository;

    @Transactional
    public AllAeroportoDTO buscarTodosAeroportos(){
        ArrayList<Aeroporto> todosAeroportos = (ArrayList<Aeroporto>) aeroportoRepository.findAll();

        if (todosAeroportos.isEmpty())
            throw new RuntimeException("Nenhum Aeroporto Encontrado");

        ArrayList<AeroportoResponseDTO> allAeroportoDTOS = new ArrayList<>();
        for (Aeroporto aeroporto : todosAeroportos){
            AeroportoResponseDTO aeroportoResponseDTO = settarResponseDTO(aeroporto);
            allAeroportoDTOS.add(aeroportoResponseDTO);
        }

        return new AllAeroportoDTO(allAeroportoDTOS);
    }


    private AeroportoResponseDTO settarResponseDTO(Aeroporto aeroporto){
        return new AeroportoResponseDTO(
                aeroporto.getIdAeroporto(),
                aeroporto.getNomeAeroporto(),
                aeroporto.getCodigoIATA(),
                aeroporto.getCidade(),
                aeroporto.getCodigoISO(),
                aeroporto.getLatitude(),
                aeroporto.getLongitude(),
                aeroporto.getAltitude()
        );
    }
}
