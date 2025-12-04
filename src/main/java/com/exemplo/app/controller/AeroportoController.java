package com.exemplo.app.controller;

import com.exemplo.app.dto.AeroportoRequestDTO;
import com.exemplo.app.dto.AeroportoResponseDTO;
import com.exemplo.app.dto.AllAeroportoDTO;
import com.exemplo.app.service.AeroportoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/aeroporto")
@AllArgsConstructor
public class AeroportoController {

    private final AeroportoService aeroportoService;

    @GetMapping("/all")
    public ResponseEntity<?> buscarTodosAeroportos(){
        try {
            AllAeroportoDTO dto = aeroportoService.buscarTodosAeroportos();
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{iata}")
    public ResponseEntity<?> buscarAeroportoPorIATA(@PathVariable String iata){
        try {
            AeroportoResponseDTO dto = aeroportoService.buscarAeroportoPorIATA(iata);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarNovoAeroporto(@RequestBody AeroportoRequestDTO dto){
        try {
            AeroportoResponseDTO responseDTO = aeroportoService.registrarNovoAeroporto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{iata}")
    public ResponseEntity<?> alterarDadosDeAeroporto(@PathVariable String iata, @RequestBody AeroportoRequestDTO dto){
        try {
            AeroportoResponseDTO responseDTO = aeroportoService.alterarDadosAeroporto(iata,dto);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{iata}")
    public ResponseEntity<?> deletarAeroporto(@PathVariable String iata){
        try {
            aeroportoService.deletarAeroporto(iata);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
