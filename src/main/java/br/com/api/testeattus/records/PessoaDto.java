package br.com.api.testeattus.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PessoaDto(
        @NotBlank(message = "O nome não pode ser vazio")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]+(\\s[A-Za-zÀ-ÿ]+)*$", message = "O nome completo é inválido")
        String nome,
        @NotNull(message = "Data de nascimento inválida")
        LocalDate dataNascimento
) {}
