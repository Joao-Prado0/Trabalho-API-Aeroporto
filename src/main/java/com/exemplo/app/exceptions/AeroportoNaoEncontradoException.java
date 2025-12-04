package com.exemplo.app.exceptions;

public class AeroportoNaoEncontradoException extends RuntimeException {
    public AeroportoNaoEncontradoException(String message) {
        super(message);
    }
}
