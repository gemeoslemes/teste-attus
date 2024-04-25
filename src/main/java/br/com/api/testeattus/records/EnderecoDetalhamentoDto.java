package br.com.api.testeattus.records;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;

public record EnderecoDetalhamentoDto(
        Long id,
        String logradouro,
        Integer cep,
        Integer numero,
        String cidade,
        Estado estado,
        Long pessoa,
        boolean favorito
) {
    public EnderecoDetalhamentoDto(Enderecos endereco) {
        this(endereco.getId(), endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade(),
            endereco.getEstado(), endereco.getPessoas().getId(), endereco.isFavorito());
    }
}
