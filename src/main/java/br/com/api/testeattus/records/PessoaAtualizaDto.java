package br.com.api.testeattus.records;

import java.time.LocalDate;

public record PessoaAtualizaDto(
        String nome,
        LocalDate dataNascimento)
{}
