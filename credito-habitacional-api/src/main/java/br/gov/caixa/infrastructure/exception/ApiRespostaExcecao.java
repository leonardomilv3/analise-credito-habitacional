package br.gov.caixa.infrastructure.exception;

import java.time.Instant;

public class ApiRespostaExcecao {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public ApiRespostaExcecao() {
    }

    public ApiRespostaExcecao(
            Integer status,
            String error,
            String message,
            String path
    ) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}