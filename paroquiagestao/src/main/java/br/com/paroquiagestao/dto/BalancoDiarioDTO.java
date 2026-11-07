package br.com.paroquiagestao.dto;

import java.time.LocalDate;

public record BalancoDiarioDTO(
        LocalDate data,
        Double totalBruto,
        Double totalDespesas,
        Double lucroLiquido
) {}