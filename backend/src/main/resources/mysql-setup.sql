-- Script de criação do banco de dados MySQL para o Sistema de Gestão de Estoque

CREATE DATABASE IF NOT EXISTS estoque_veiculos;
USE estoque_veiculos;

-- Tabela de Marcas
CREATE TABLE IF NOT EXISTS marca (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabela de Modelos
CREATE TABLE IF NOT EXISTS modelo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    marca_id BIGINT NOT NULL,
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marca(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela de Veículos
CREATE TABLE IF NOT EXISTS veiculo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    modelo_id BIGINT NOT NULL,
    ano INT NOT NULL,
    cor VARCHAR(50) NOT NULL,
    preco DECIMAL(12, 2) NOT NULL,
    quilometragem INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelo(id) ON DELETE CASCADE,
    INDEX idx_veiculo_status (status),
    INDEX idx_veiculo_preco (preco),
    INDEX idx_veiculo_ano (ano)
) ENGINE=InnoDB;

-- Dados Iniciais para Teste
INSERT INTO marca (nome) VALUES ('Toyota'), ('Honda'), ('Ford'), ('Volkswagen'), ('Chevrolet');

INSERT INTO modelo (nome, marca_id) VALUES 
('Corolla', 1), ('Hilux', 1), 
('Civic', 2), ('Fit', 2),
('Mustang', 3), ('Ranger', 3),
('Golf', 4), ('Polo', 4),
('Onix', 5), ('S10', 5);

INSERT INTO veiculo (modelo_id, ano, cor, preco, quilometragem, status) VALUES
(1, 2022, 'Prata', 120000.00, 15000, 'Disponível'),
(3, 2021, 'Preto', 150000.00, 20000, 'Disponível'),
(5, 2023, 'Vermelho', 350000.00, 5000, 'Disponível'),
(7, 2020, 'Branco', 110000.00, 45000, 'Vendido'),
(9, 2019, 'Azul', 65000.00, 60000, 'Disponível');
