package com.exemplo.estoque.repository;

import com.exemplo.estoque.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByMarcaId(Long marcaId);
}
