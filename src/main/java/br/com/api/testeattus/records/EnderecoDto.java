package br.com.api.testeattus.records;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDto (
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,
        @NotNull(message = "CEP é obrigatório.")
        Integer cep,
        @NotNull(message = "Número é obrigatório.")
        Integer numero,
        @NotBlank(message = "Cidade é obrigatório.")
        String cidade,
        @NotNull(message = "Estado é obrigatório.")
        Estado estado,
        @NotNull(message = "Campo obrigatório.")
        Long pessoa,

        boolean favorito
) {}
