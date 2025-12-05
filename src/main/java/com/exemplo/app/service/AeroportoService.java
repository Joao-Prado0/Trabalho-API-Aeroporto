package com.exemplo.app.service;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.dto.AeroportoResponseDTO;
import com.exemplo.app.dto.AllAeroportoDTO;
import com.exemplo.app.exceptions.AeroportoNaoEncontradoException;
import com.exemplo.app.model.Aeroporto;
import com.exemplo.app.repository.AeroportoRepository;
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
            throw new AeroportoNaoEncontradoException("Nenhum Aeroporto Encontrado");

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
            throw new AeroportoNaoEncontradoException("Nenhum aeroporto encotrado com IATA: " + IATA);

        return settarResponseDTO(aeroporto.get());
    }

    @Transactional
    public AeroportoResponseDTO registrarNovoAeroporto(AeroportoRequestDTO dto){

        Optional<Aeroporto> aeroporto = aeroportoRepository.findByCodigoIATA(dto.codigoIATA());

        if (aeroporto.isPresent())
            throw new AeroportoNaoEncontradoException("Aeroporto já existe no banco de dados com IATA: " + dto.codigoIATA());

        Aeroporto novoAeroporto = settarAtributosAeroporto(new Aeroporto(), dto);

        aeroportoRepository.save(novoAeroporto);

        return settarResponseDTO(novoAeroporto);
    }

    @Transactional
    public AeroportoResponseDTO alterarDadosAeroporto(String iata, AeroportoRequestDTO dto){

        if (!iata.equals(dto.codigoIATA()))
            throw new RuntimeException("IATA da url não corresponde ao IATA da requisição");

        Optional<Aeroporto> aeroportoOPT = aeroportoRepository.findByCodigoIATA(iata);
        if (aeroportoOPT.isEmpty())
            throw new AeroportoNaoEncontradoException("Aeroporto não encontrado no BD com código IATA: " + iata);

        Aeroporto aeroportoBD = settarAtributosAeroporto(aeroportoOPT.get(), dto);

        aeroportoRepository.save(aeroportoBD);

        return settarResponseDTO(aeroportoBD);
    }

    @Transactional
    public void deletarAeroporto(String iata){
        Optional<Aeroporto> aeroporto = aeroportoRepository.findByCodigoIATA(iata);

        if (aeroporto.isEmpty())
            throw new AeroportoNaoEncontradoException("Aeroporto não encontrado no BD com código IATA: " + iata);

        aeroportoRepository.delete(aeroporto.get());
    }

    // ----------------------------- Métodos utilitários -------------------------------------

    private Aeroporto settarAtributosAeroporto(Aeroporto aeroporto, AeroportoRequestDTO dto){
        aeroporto.setNomeAeroporto(dto.nomeAeroporto());
        aeroporto.setCidade(dto.cidade());

        if (dto.longitude()<-180 || dto.longitude()>180)
            throw new IllegalArgumentException("Longitude inválida");
        aeroporto.setLongitude(dto.longitude());

        if (dto.latitude()<-90.0 || dto.latitude()>90.0)
            throw new IllegalArgumentException("Latitude inválida");
        aeroporto.setLatitude(dto.latitude());

        if (dto.codigoIATA().length() > 3)
            throw new IllegalArgumentException("Código IATA inválido");
        aeroporto.setCodigoIATA(dto.codigoIATA());

        if(dto.codigoISO().length() > 2)
            throw new IllegalArgumentException("Código ISO inválido");
        aeroporto.setCodigoISO(dto.codigoISO());

        if (dto.altitude() < -378.0)
            throw new IllegalArgumentException("Altitude Inválida");
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
