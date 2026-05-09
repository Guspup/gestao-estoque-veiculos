package com.exemplo.estoque.controller;

import com.exemplo.estoque.dto.MarcaDTO;
import com.exemplo.estoque.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @GetMapping
    public List<MarcaDTO> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public MarcaDTO salvar(@RequestBody MarcaDTO dto) {
        return service.salvar(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}
