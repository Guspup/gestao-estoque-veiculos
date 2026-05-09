package com.exemplo.estoque.dto;

import com.exemplo.estoque.model.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para representar um Modelo com validação.
 */
public record ModeloDTO(
    Long id,
    
    @NotBlank(message = "O nome do modelo é obrigatório")
    String nome,
    
    @NotNull(message = "A marca é obrigatória")
    MarcaDTO marca
) {
    public static ModeloDTO fromEntity(Modelo modelo) {
        return new ModeloDTO(
            modelo.getId(),
            modelo.getNome(),
            modelo.getMarca() != null ? MarcaDTO.fromEntity(modelo.getMarca()) : null
        );
    }

    public Modelo toEntity() {
        Modelo modelo = new Modelo();
        modelo.setId(this.id);
        modelo.setNome(this.nome);
        if (this.marca != null) {
            modelo.setMarca(this.marca.toEntity());
        }
        return modelo;
    }
}
