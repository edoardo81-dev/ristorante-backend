package com.example.controller;

import com.example.dto.PiattoDTO;
import com.example.service.PiattoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/piatti")
public class PiattoController {

    private final PiattoService service;

    @GetMapping
    public List<PiattoDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PiattoDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<PiattoDTO> getByCategoria(@PathVariable String categoria) {
        return service.findByCategoria(categoria);
    }

    /** Endpoint ordinato per il form “conto”. */
    @GetMapping("/ordered")
    public List<PiattoDTO> getAllOrderedForBill() {
        return service.findAllOrderedForBill();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PiattoDTO create(@RequestBody PiattoDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    public PiattoDTO update(@PathVariable Long id, @RequestBody @jakarta.validation.Valid PiattoDTO dto) {
        return service.update(id, dto);
    }
}
