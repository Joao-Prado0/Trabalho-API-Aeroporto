package com.exemplo.app.controller;

import com.exemplo.app.dto.AeroportoRegisterDTO;
import com.exemplo.app.dto.AeroportoResponseDTO;
import com.exemplo.app.dto.AllAeroportoDTO;
import com.exemplo.app.service.AeroportoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAeroportoPorID(@PathVariable Integer id){
        try {
            AeroportoResponseDTO dto = aeroportoService.buscarAeroportoPorID(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarNovoAeroporto(AeroportoRegisterDTO dto){
        try {
            aeroportoService.registrarNovoAeroporto(dto);
            return ResponseEntity.ok("Aeroporto criado com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
