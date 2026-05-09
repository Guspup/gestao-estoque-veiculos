package com.exemplo.estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "veiculo", indexes = {
    @Index(name = "idx_veiculo_status", columnList = "status"),
    @Index(name = "idx_veiculo_preco", columnList = "preco"),
    @Index(name = "idx_veiculo_ano", columnList = "ano")
})
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O modelo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1900, message = "O ano deve ser maior que 1900")
    @Max(value = 2100, message = "O ano deve ser menor que 2100")
    private Integer ano;

    @NotBlank(message = "A cor é obrigatória")
    private String cor;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço não pode ser negativo")
    private BigDecimal preco;

    @NotNull(message = "A quilometragem é obrigatória")
    @Min(value = 0, message = "A quilometragem não pode ser negativa")
    private Integer quilometragem;

    @NotBlank(message = "O status é obrigatório")
    private String status; // Disponível, Vendido, etc.

    public Veiculo() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Modelo getModelo() { return modelo; }
    public void setModelo(Modelo modelo) { this.modelo = modelo; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getQuilometragem() { return quilometragem; }
    public void setQuilometragem(Integer quilometragem) { this.quilometragem = quilometragem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
