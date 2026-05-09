package com.exemplo.estoque.service;

import com.exemplo.estoque.dto.MarcaDTO;
import com.exemplo.estoque.model.Marca;
import com.exemplo.estoque.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar regras de negócio de Marcas.
 */
@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;

    @Transactional(readOnly = true)
    public List<MarcaDTO> listarTodas() {
        return repository.findAll().stream()
                .map(MarcaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public MarcaDTO salvar(MarcaDTO dto) {
        Marca marca = dto.toEntity();
        return MarcaDTO.fromEntity(repository.save(marca));
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
