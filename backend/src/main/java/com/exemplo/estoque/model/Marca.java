package com.exemplo.estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(min = 2, max = 50, message = "O nome da marca deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;

    public Marca() {}

    public Marca(String nome) {
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
