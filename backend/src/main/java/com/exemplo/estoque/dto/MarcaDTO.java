package com.exemplo.estoque.dto;

import com.exemplo.estoque.model.Marca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para representar uma Marca com validação.
 */
public record MarcaDTO(
    Long id,
    
    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(min = 2, max = 50, message = "O nome da marca deve ter entre 2 e 50 caracteres")
    String nome
) {
    public static MarcaDTO fromEntity(Marca marca) {
        return new MarcaDTO(marca.getId(), marca.getNome());
    }

    public Marca toEntity() {
        Marca marca = new Marca();
        marca.setId(this.id);
        marca.setNome(this.nome);
        return marca;
    }
}
