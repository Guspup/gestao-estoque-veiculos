package com.exemplo.estoque.config;

import com.exemplo.estoque.model.Marca;
import com.exemplo.estoque.model.Modelo;
import com.exemplo.estoque.model.Veiculo;
import com.exemplo.estoque.repository.MarcaRepository;
import com.exemplo.estoque.repository.ModeloRepository;
import com.exemplo.estoque.repository.VeiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DadosIniciais {

    @Bean
    CommandLineRunner initDatabase(MarcaRepository marcaRepo, ModeloRepository modeloRepo, VeiculoRepository veiculoRepo) {
        return args -> {
            if (marcaRepo.count() == 0) {
                // --- MARCAS ---
                Marca toyota = marcaRepo.save(new Marca("Toyota"));
                Marca honda = marcaRepo.save(new Marca("Honda"));
                Marca jeep = marcaRepo.save(new Marca("Jeep"));
                Marca vw = marcaRepo.save(new Marca("Volkswagen"));
                Marca fiat = marcaRepo.save(new Marca("Fiat"));
                Marca chevrolet = marcaRepo.save(new Marca("Chevrolet"));
                Marca hyundai = marcaRepo.save(new Marca("Hyundai"));

                // --- MODELOS ---
                Modelo corolla = modeloRepo.save(new Modelo("Corolla XEi", toyota));
                Modelo hilux = modeloRepo.save(new Modelo("Hilux SRV", toyota));
                Modelo civic = modeloRepo.save(new Modelo("Civic LXR", honda));
                Modelo hrv = modeloRepo.save(new Modelo("HR-V Touring", honda));
                Modelo renegade = modeloRepo.save(new Modelo("Renegade Longitude", jeep));
                Modelo compass = modeloRepo.save(new Modelo("Compass Limited", jeep));
                Modelo tcross = modeloRepo.save(new Modelo("T-Cross Highline", vw));
                Modelo gol = modeloRepo.save(new Modelo("Gol MPI", vw));
                Modelo toro = modeloRepo.save(new Modelo("Toro Volcano", fiat));
                Modelo mobi = modeloRepo.save(new Modelo("Mobi Like", fiat));
                Modelo onix = modeloRepo.save(new Modelo("Onix LTZ", chevrolet));
                Modelo tracker = modeloRepo.save(new Modelo("Tracker Premier", chevrolet));
                Modelo hb20 = modeloRepo.save(new Modelo("HB20 Comfort", hyundai));
                Modelo creta = modeloRepo.save(new Modelo("Creta Ultimate", hyundai));

                // --- VEÍCULOS (DADOS REAIS DE MERCADO) ---
                
                // Toyota
                veiculoRepo.save(criarVeiculo(corolla, 2022, "Prata", "138500", 25400, "Disponível"));
                veiculoRepo.save(criarVeiculo(hilux, 2020, "Branco", "215900", 78000, "Disponível"));
                
                // Honda
                veiculoRepo.save(criarVeiculo(civic, 2017, "Preto", "94800", 82000, "Disponível"));
                veiculoRepo.save(criarVeiculo(hrv, 2021, "Cinza", "125000", 35000, "Vendido"));
                
                // Jeep
                veiculoRepo.save(criarVeiculo(renegade, 2019, "Vermelho", "89900", 55000, "Disponível"));
                veiculoRepo.save(criarVeiculo(compass, 2022, "Azul Jazz", "168000", 18500, "Disponível"));
                
                // VW
                veiculoRepo.save(criarVeiculo(tcross, 2023, "Branco", "142000", 12000, "Disponível"));
                veiculoRepo.save(criarVeiculo(gol, 2015, "Prata", "38500", 98000, "Disponível"));
                
                // Fiat
                veiculoRepo.save(criarVeiculo(toro, 2021, "Marrom", "132900", 42000, "Disponível"));
                veiculoRepo.save(criarVeiculo(mobi, 2023, "Branco", "58900", 5000, "Disponível"));
                
                // Chevrolet
                veiculoRepo.save(criarVeiculo(onix, 2020, "Azul", "72500", 48000, "Vendido"));
                veiculoRepo.save(criarVeiculo(tracker, 2022, "Cinza", "118000", 28000, "Disponível"));
                
                // Hyundai
                veiculoRepo.save(criarVeiculo(hb20, 2018, "Branco", "52900", 72000, "Disponível"));
                veiculoRepo.save(criarVeiculo(creta, 2022, "Azul", "129900", 22000, "Em Manutenção"));

                // Mais alguns para volume
                veiculoRepo.save(criarVeiculo(corolla, 2015, "Branco", "78000", 110000, "Disponível"));
                veiculoRepo.save(criarVeiculo(civic, 2020, "Branco", "115000", 45000, "Disponível"));
                veiculoRepo.save(criarVeiculo(onix, 2023, "Preto", "85000", 8000, "Disponível"));
                veiculoRepo.save(criarVeiculo(renegade, 2021, "Verde Militar", "105000", 30000, "Disponível"));
                veiculoRepo.save(criarVeiculo(tcross, 2021, "Cinza", "110000", 40000, "Vendido"));
                veiculoRepo.save(criarVeiculo(hb20, 2022, "Prata", "75000", 15000, "Disponível"));
            }
        };
    }

    private Veiculo criarVeiculo(Modelo modelo, Integer ano, String cor, String preco, Integer km, String status) {
        Veiculo v = new Veiculo();
        v.setModelo(modelo);
        v.setAno(ano);
        v.setCor(cor);
        v.setPreco(new BigDecimal(preco));
        v.setQuilometragem(km);
        v.setStatus(status);
        return v;
    }
}
