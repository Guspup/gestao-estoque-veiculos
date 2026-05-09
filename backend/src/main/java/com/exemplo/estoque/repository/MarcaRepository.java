package com.exemplo.estoque.repository;

import com.exemplo.estoque.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Marca findByNome(String nome);
}
