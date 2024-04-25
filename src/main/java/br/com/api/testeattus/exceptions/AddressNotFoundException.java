package br.com.api.testeattus.exceptions;


import java.util.NoSuchElementException;

public class AddressNotFoundException extends NoSuchElementException {
    public AddressNotFoundException(String s) {
        super(s);
    }

    public AddressNotFoundException() {
        super("Endereço não encontrado!");
    }
}
