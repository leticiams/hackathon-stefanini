package com.stefanini.exception;

public class NegocioException extends Exception {

    private String mensagem;
    public NegocioException(String s) {
        mensagem = s;
    }

    public String getMensagem() {
        return mensagem;
    }
}
