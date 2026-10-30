package br.com.paroquiagestao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record VendaRequest(
        @NotNull(message = "O produto é obrigatório") Long produtoId,
        @NotNull(message = "O grupo é obrigatório") Long grupoId,
        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero") Integer quantidade,
        LocalDate data
) {}