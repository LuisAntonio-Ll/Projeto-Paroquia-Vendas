package br.com.paroquiagestao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório") String nome,
        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero") Double precoVenda
) {}