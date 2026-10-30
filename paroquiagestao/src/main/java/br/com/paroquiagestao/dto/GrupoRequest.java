package br.com.paroquiagestao.dto;

import jakarta.validation.constraints.NotBlank;

public record GrupoRequest(
        @NotBlank(message = "O nome do grupo é obrigatório") String nome,
        @NotBlank(message = "O responsável é obrigatório") String responsavel
) {}