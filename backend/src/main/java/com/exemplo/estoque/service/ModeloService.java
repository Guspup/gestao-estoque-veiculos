package com.exemplo.estoque.service;

import com.exemplo.estoque.dto.ModeloDTO;
import com.exemplo.estoque.model.Marca;
import com.exemplo.estoque.model.Modelo;
import com.exemplo.estoque.repository.MarcaRepository;
import com.exemplo.estoque.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar regras de negócio de Modelos com tratamento de entidades relacionadas.
 */
@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public List<ModeloDTO> listarPorMarca(Long marcaId) {
        List<Modelo> modelos;
        if (marcaId != null) {
            modelos = repository.findByMarcaId(marcaId);
        } else {
            modelos = repository.findAll();
        }
        return modelos.stream()
                .map(ModeloDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ModeloDTO salvar(ModeloDTO dto) {
        if (dto.marca() == null || dto.marca().id() == null) {
            throw new RuntimeException("ID da Marca é obrigatório para cadastrar um modelo.");
        }
        
        Marca marca = marcaRepository.findById(dto.marca().id())
                .orElseThrow(() -> new RuntimeException("Marca não encontrada com ID: " + dto.marca().id()));
        
        Modelo modelo = new Modelo();
        modelo.setNome(dto.nome());
        modelo.setMarca(marca);
        
        return ModeloDTO.fromEntity(repository.save(modelo));
    }
}
