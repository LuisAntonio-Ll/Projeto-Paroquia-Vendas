package br.com.paroquiagestao.controller;

import br.com.paroquiagestao.dto.VendaRequest;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<Venda> registrar(@Valid @RequestBody VendaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vendaService.registrar(request));
    }
}