package com.exemplo.estoque.controller;

import com.exemplo.estoque.dto.VeiculoDTO;
import com.exemplo.estoque.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @GetMapping
    public List<VeiculoDTO> listar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) BigDecimal precoMax) {
        return service.listarComFiltros(marca, modelo, status, ano, precoMax);
    }

    @PostMapping
    public VeiculoDTO salvar(@jakarta.validation.Valid @RequestBody VeiculoDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizar(@PathVariable Long id, @jakarta.validation.Valid @RequestBody VeiculoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
