package br.gov.caixa.infrastructure.exception;

public class NaoAutorizaoExcecao extends RuntimeException {

    public NaoAutorizaoExcecao(String message) {
        super(message);
    }
}