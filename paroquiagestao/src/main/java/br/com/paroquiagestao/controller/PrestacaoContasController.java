package br.com.paroquiagestao.controller;

import br.com.paroquiagestao.dto.BalancoAnualDTO;
import br.com.paroquiagestao.dto.BalancoDiarioDTO;
import br.com.paroquiagestao.dto.BalancoFinanceiroDTO;
import br.com.paroquiagestao.dto.BalancoMensalDTO;
import br.com.paroquiagestao.service.PrestacaoContasService;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestacao-contas")
public class PrestacaoContasController {

    private final PrestacaoContasService prestacaoContasService;

    public PrestacaoContasController(PrestacaoContasService prestacaoContasService) {
        this.prestacaoContasService = prestacaoContasService;
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<BalancoFinanceiroDTO> gerarBalanco(@PathVariable Long grupoId) {
        return ResponseEntity.ok(prestacaoContasService.gerarBalanco(grupoId));
    }

    @GetMapping("/{grupoId}/mensal")
    public ResponseEntity<BalancoMensalDTO> gerarBalancoMensal(
            @PathVariable Long grupoId,
            @RequestParam int ano,
            @RequestParam int mes) {
        return ResponseEntity.ok(prestacaoContasService.gerarBalancoMensal(grupoId, ano, mes));
    }

    @GetMapping("/{grupoId}/anual")
    public ResponseEntity<BalancoAnualDTO> gerarBalancoAnual(
            @PathVariable Long grupoId,
            @RequestParam int ano) {
        return ResponseEntity.ok(prestacaoContasService.gerarBalancoAnual(grupoId, ano));
    }

    @GetMapping("/{grupoId}/dia")
    public ResponseEntity<BalancoDiarioDTO> gerarBalancoDiario(
            @PathVariable Long grupoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return ResponseEntity.ok(prestacaoContasService.gerarBalancoDiario(grupoId, data));
    }
}