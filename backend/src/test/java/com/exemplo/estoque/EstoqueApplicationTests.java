package com.exemplo.estoque;

import com.exemplo.estoque.model.Marca;
import com.exemplo.estoque.model.Modelo;
import com.exemplo.estoque.model.Veiculo;
import com.exemplo.estoque.repository.MarcaRepository;
import com.exemplo.estoque.repository.ModeloRepository;
import com.exemplo.estoque.repository.VeiculoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstoqueApplicationTests {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Test
    void deveSalvarEBucarVeiculoComFiltro() {
        Marca toyota = marcaRepository.save(new Marca("Toyota"));
        Modelo corolla = modeloRepository.save(new Modelo("Corolla", toyota));
        
        Veiculo v = new Veiculo();
        v.setModelo(corolla);
        v.setAno(2022);
        v.setCor("Prata");
        v.setPreco(new BigDecimal("150000"));
        v.setQuilometragem(0);
        v.setStatus("Disponível");
        
        veiculoRepository.save(v);

        List<Veiculo> filtrados = veiculoRepository.buscarComFiltros("Toyota", null, null, null, null);
        
        assertThat(filtrados).hasSize(1);
        assertThat(filtrados.get(0).getModelo().getNome()).isEqualTo("Corolla");
    }
}
