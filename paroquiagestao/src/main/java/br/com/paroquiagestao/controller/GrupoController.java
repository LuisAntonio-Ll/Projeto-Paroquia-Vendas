package br.com.paroquiagestao.controller;

import br.com.paroquiagestao.dto.GrupoRequest;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @PostMapping
    public ResponseEntity<Grupo> cadastrar(@Valid @RequestBody GrupoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(grupoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> listar() {
        return ResponseEntity.ok(grupoService.listar());
    }
}