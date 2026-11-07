package br.com.paroquiagestao.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResposta(
        int status,
        String mensagem,
        List<String> detalhes,
        LocalDateTime timestamp
) {
    public ErroResposta(int status, String mensagem, List<String> detalhes) {
        this(status, mensagem, detalhes, LocalDateTime.now());
    }
}