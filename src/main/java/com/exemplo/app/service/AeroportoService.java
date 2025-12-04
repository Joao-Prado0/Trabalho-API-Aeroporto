package com.exemplo.app.service;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.dto.AeroportoResponseDTO;
import com.exemplo.app.dto.AllAeroportoDTO;
import com.exemplo.app.model.Aeroporto;
import com.exemplo.app.repositiory.AeroportoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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

    @Transactional
    public AeroportoResponseDTO buscarAeroportoPorIATA(String IATA){
        Optional<Aeroporto> aeroporto = aeroportoRepository.findByCodigoIATA(IATA);

        if (aeroporto.isEmpty())
            throw new RuntimeException("Nenhum aeroporto encotrado com IATA: " + IATA);

        return settarResponseDTO(aeroporto.get());
    }

    @Transactional
    public void registrarNovoAeroporto(AeroportoRequestDTO dto){

        Optional<Aeroporto> aeroporto = aeroportoRepository.findByCodigoIATA(dto.codigoIATA());

        if (aeroporto.isPresent())
            throw new RuntimeException("Aeroporto já existe no banco de dados com IATA: " + dto.codigoIATA());

        Aeroporto novoAeroporto = settarAtributosAeroporto(aeroporto.get(), dto);

        aeroportoRepository.save(novoAeroporto);
    }

    @Transactional
    public void alterarDadosAeroporto(String iata, AeroportoRequestDTO dto){

        if (!iata.equals(dto.codigoIATA()))
            throw new RuntimeException("IATA da url não corresponde ao IATA da requisição");

        Optional<Aeroporto> aeroportoOPT = aeroportoRepository.findByCodigoIATA(iata);
        if (aeroportoOPT.isEmpty())
            throw new RuntimeException("Aeroporto não encontrado no BD com código IATA: " + iata);

        Aeroporto aeroportoBD = settarAtributosAeroporto(aeroportoOPT.get(), dto);


    }

    // ----------------------------- Métodos utilitários -------------------------------------

    private Aeroporto settarAtributosAeroporto(Aeroporto aeroporto, AeroportoRequestDTO dto){
        aeroporto.setNomeAeroporto(dto.nomeAeroporto());
        aeroporto.setCodigoIATA(dto.codigoIATA());
        aeroporto.setCidade(dto.cidade());
        aeroporto.setCodigoISO(dto.codigoISO());
        aeroporto.setLatitude(dto.latitude());
        aeroporto.setLongitude(dto.longitude());
        aeroporto.setAltitude(dto.altitude());

        return aeroporto;
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
