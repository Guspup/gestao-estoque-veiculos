package com.exemplo.estoque.service;

import com.exemplo.estoque.dto.VeiculoDTO;
import com.exemplo.estoque.model.Modelo;
import com.exemplo.estoque.model.Veiculo;
import com.exemplo.estoque.repository.ModeloRepository;
import com.exemplo.estoque.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar regras de negócio de Veículos com tratamento de entidades relacionadas.
 */
@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(readOnly = true)
    public List<VeiculoDTO> listarComFiltros(String marca, String modelo, String status, Integer ano, BigDecimal precoMax) {
        return repository.buscarComFiltros(marca, modelo, status, ano, precoMax).stream()
                .map(VeiculoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public VeiculoDTO salvar(VeiculoDTO dto) {
        if (dto.modelo() == null || dto.modelo().id() == null) {
            throw new RuntimeException("O modelo do veículo é obrigatório.");
        }

        Modelo modelo = modeloRepository.findById(dto.modelo().id())
                .orElseThrow(() -> new RuntimeException("Modelo não encontrado com ID: " + dto.modelo().id()));

        Veiculo veiculo = new Veiculo();
        mapearDtoParaEntidade(dto, veiculo);
        veiculo.setModelo(modelo);

        return VeiculoDTO.fromEntity(repository.save(veiculo));
    }

    @Transactional
    public VeiculoDTO atualizar(Long id, VeiculoDTO dto) {
        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com ID: " + id));

        if (dto.modelo() != null && dto.modelo().id() != null) {
            Modelo modelo = modeloRepository.findById(dto.modelo().id())
                    .orElseThrow(() -> new RuntimeException("Modelo não encontrado com ID: " + dto.modelo().id()));
            veiculo.setModelo(modelo);
        }

        mapearDtoParaEntidade(dto, veiculo);
        return VeiculoDTO.fromEntity(repository.save(veiculo));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Veículo não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    private void mapearDtoParaEntidade(VeiculoDTO dto, Veiculo veiculo) {
        veiculo.setAno(dto.ano());
        veiculo.setCor(dto.cor());
        veiculo.setPreco(dto.preco());
        veiculo.setQuilometragem(dto.quilometragem());
        veiculo.setStatus(dto.status());
    }
}
