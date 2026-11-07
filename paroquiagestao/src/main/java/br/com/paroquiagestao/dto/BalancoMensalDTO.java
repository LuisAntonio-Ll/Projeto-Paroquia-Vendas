package br.com.paroquiagestao.dto;

public record BalancoMensalDTO(
        int ano,
        int mes,
        Double totalBruto,
        Double totalDespesas,
        Double lucroLiquido
) {}