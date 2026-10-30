package br.com.paroquiagestao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record DespesaRequest(
        @NotBlank(message = "A descrição é obrigatória") String descricao,
        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero") Double valor,
        @NotNull(message = "O grupo é obrigatório") Long grupoId,
        LocalDate data
) {}