package br.com.paroquiagestao.controller;

import br.com.paroquiagestao.dto.DespesaRequest;
import br.com.paroquiagestao.model.Despesa;
import br.com.paroquiagestao.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping
    public ResponseEntity<Despesa> registrar(@Valid @RequestBody DespesaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(despesaService.registrar(request));
    }
}