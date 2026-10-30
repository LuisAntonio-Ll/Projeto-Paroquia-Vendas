package br.com.paroquiagestao.dto;

public record BalancoFinanceiroDTO(
        Long grupoId,
        String grupoNome,
        Double totalBruto,
        Double totalDespesas,
        Double lucroLiquido
) {}