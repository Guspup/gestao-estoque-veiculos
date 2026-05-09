package com.exemplo.estoque.dto;

import com.exemplo.estoque.model.Veiculo;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para representar um Veículo com validação.
 */
public record VeiculoDTO(
    Long id,
    
    @NotNull(message = "O modelo é obrigatório")
    ModeloDTO modelo,
    
    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1900, message = "O ano deve ser maior que 1900")
    @Max(value = 2100, message = "O ano deve ser menor que 2100")
    Integer ano,
    
    @NotBlank(message = "A cor é obrigatória")
    String cor,
    
    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço não pode ser negativo")
    BigDecimal preco,
    
    @NotNull(message = "A quilometragem é obrigatória")
    @Min(value = 0, message = "A quilometragem não pode ser negativa")
    Integer quilometragem,
    
    @NotBlank(message = "O status é obrigatório")
    String status
) {
    public static VeiculoDTO fromEntity(Veiculo veiculo) {
        return new VeiculoDTO(
            veiculo.getId(),
            veiculo.getModelo() != null ? ModeloDTO.fromEntity(veiculo.getModelo()) : null,
            veiculo.getAno(),
            veiculo.getCor(),
            veiculo.getPreco(),
            veiculo.getQuilometragem(),
            veiculo.getStatus()
        );
    }

    public Veiculo toEntity() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(this.id);
        veiculo.setAno(this.ano);
        veiculo.setCor(this.cor);
        veiculo.setPreco(this.preco);
        veiculo.setQuilometragem(this.quilometragem);
        veiculo.setStatus(this.status);
        if (this.modelo != null) {
            veiculo.setModelo(this.modelo.toEntity());
        }
        return veiculo;
    }
}
