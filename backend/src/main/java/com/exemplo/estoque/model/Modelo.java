package com.exemplo.estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "A marca é obrigatória")
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public Modelo() {}

    public Modelo(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }
}
