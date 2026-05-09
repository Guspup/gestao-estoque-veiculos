package com.exemplo.estoque.controller;

import com.exemplo.estoque.dto.ModeloDTO;
import com.exemplo.estoque.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin(origins = "*")
public class ModeloController {

    @Autowired
    private ModeloService service;

    @GetMapping
    public List<ModeloDTO> listar(@RequestParam(required = false) Long marcaId) {
        return service.listarPorMarca(marcaId);
    }

    @PostMapping
    public ModeloDTO salvar(@RequestBody ModeloDTO dto) {
        return service.salvar(dto);
    }
}
