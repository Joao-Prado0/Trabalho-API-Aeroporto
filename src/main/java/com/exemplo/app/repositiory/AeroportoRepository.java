package com.exemplo.app.repositiory;

import com.exemplo.app.model.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

}
