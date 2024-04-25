package br.com.api.testeattus.records;

import br.com.api.testeattus.enuns.Estado;

public record EnderecoAtualizaDto(
        String logradouro,
        Integer cep,
        Integer numero,
        String cidade,
        Estado estado,
        boolean favorito
) {
}
