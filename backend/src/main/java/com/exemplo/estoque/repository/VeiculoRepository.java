package com.exemplo.estoque.repository;

import com.exemplo.estoque.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    @Query("SELECT v FROM Veiculo v WHERE " +
           "(:marca IS NULL OR v.modelo.marca.nome LIKE %:marca%) AND " +
           "(:modelo IS NULL OR v.modelo.nome LIKE %:modelo%) AND " +
           "(:status IS NULL OR v.status = :status) AND " +
           "(:ano IS NULL OR v.ano = :ano) AND " +
           "(:precoMax IS NULL OR v.preco <= :precoMax)")
    List<Veiculo> buscarComFiltros(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("status") String status,
            @Param("ano") Integer ano,
            @Param("precoMax") BigDecimal precoMax);
}
