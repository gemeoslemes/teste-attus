package br.com.api.testeattus.exceptions;

public class EnderecoDuplicadoException extends RuntimeException {
    public EnderecoDuplicadoException(String enderecoJaExiste) {
        super(enderecoJaExiste);
    }
    public EnderecoDuplicadoException() {
        super("Endereço já existente");
    }
}
