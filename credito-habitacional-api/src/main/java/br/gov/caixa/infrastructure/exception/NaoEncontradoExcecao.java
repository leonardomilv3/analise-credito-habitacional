package br.gov.caixa.infrastructure.exception;

public class NaoEncontradoExcecao extends RuntimeException {

    public NaoEncontradoExcecao(String message) {
        super(message);
    }
}
