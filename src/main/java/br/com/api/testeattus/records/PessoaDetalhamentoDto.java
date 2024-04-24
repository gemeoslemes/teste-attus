package br.com.api.testeattus.records;

import br.com.api.testeattus.domain.Pessoa;

import java.time.LocalDate;

public record PessoaDetalhamentoDto(Long id, String nomeCompleto, LocalDate dataNascimento) {
    public PessoaDetalhamentoDto(Pessoa pessoa) {
        this(pessoa.getId(), pessoa.getNomeCompleto(), pessoa.getDataNascimento());
    }
}
