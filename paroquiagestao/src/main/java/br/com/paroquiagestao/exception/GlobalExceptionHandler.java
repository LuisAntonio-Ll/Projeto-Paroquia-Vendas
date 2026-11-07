package br.com.paroquiagestao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import java.time.DateTimeException;
import java.util.List;

@RestControllerAdvice(basePackages = "br.com.paroquiagestao.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleNaoEncontrado(RecursoNaoEncontradoException ex) {
        ErroResposta erro = new ErroResposta(404, ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
        ErroResposta erro = new ErroResposta(400, "Dados inválidos", detalhes);
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleJsonInvalido(HttpMessageNotReadableException ex) {
        ErroResposta erro = new ErroResposta(400,
                "Corpo da requisição inválido ou mal formatado", List.of());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenerico(Exception ex) {
        ex.printStackTrace(); // Log do erro para depuração
        ErroResposta erro = new ErroResposta(500,
                "Erro interno do servidor", List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErroResposta> handleDataInvalida(DateTimeException ex) {
    ErroResposta erro = new ErroResposta(400,
            "Ano ou mês inválido", List.of(ex.getMessage()));
    return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> handleTipoParametro(MethodArgumentTypeMismatchException ex) {
    ErroResposta erro = new ErroResposta(400,
            "Parâmetro inválido", List.of(ex.getName() + ": valor incorreto"));
    return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResposta> handleParametroAusente(MissingServletRequestParameterException ex) {
    ErroResposta erro = new ErroResposta(400,
            "Parâmetro obrigatório ausente", List.of(ex.getParameterName()));
    return ResponseEntity.badRequest().body(erro);
    }
}