package br.com.paroquiagestao.dto;

import java.util.List;

public record BalancoAnualDTO(
        Long grupoId,
        String grupoNome,
        int ano,
        Double totalBruto,
        Double totalDespesas,
        Double lucroLiquido,
        List<BalancoMensalDTO> meses
) {}