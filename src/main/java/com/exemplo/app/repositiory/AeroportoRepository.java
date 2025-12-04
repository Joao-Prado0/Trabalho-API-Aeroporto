package com.exemplo.app.repositiory;

import com.exemplo.app.model.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {
    Optional<Aeroporto> findByCodigoIATA(String codigoIATA);
}
