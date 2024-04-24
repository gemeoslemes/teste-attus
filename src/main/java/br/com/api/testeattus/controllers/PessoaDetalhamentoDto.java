package br.com.api.testeattus.controllers;

import br.com.api.testeattus.domain.Pessoa;

import java.time.LocalDate;

public record PessoaDetalhamentoDto(String nomeCompleto, LocalDate dataNascimento) {
    public PessoaDetalhamentoDto(Pessoa pessoa) {
        this(pessoa.getNomeCompleto(), pessoa.getDataNascimento());
    }
}
