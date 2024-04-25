package br.com.api.testeattus.exceptions;

import br.com.api.testeattus.domain.Pessoa;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class ViolationOfArgumentsException extends RuntimeException {
    public ViolationOfArgumentsException(String message) {
        super(message);
    }
    public ViolationOfArgumentsException() {
        super("Argumento inválido!");
    }
}
