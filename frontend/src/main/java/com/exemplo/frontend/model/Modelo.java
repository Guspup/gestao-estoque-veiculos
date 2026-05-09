package com.exemplo.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Modelo {
    private Long id;
    private String nome;
    private Marca marca;

    public Modelo() {}
    public Modelo(String nome, Marca marca) { this.nome = nome; this.marca = marca; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }

    @Override
    public String toString() { return nome; }
}
