package br.com.api.testeattus.controllers;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.records.PessoaDto;
import br.com.api.testeattus.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @PostMapping
    public ResponseEntity<PessoaDetalhamentoDto> create(@RequestBody @Valid PessoaDto dto, UriComponentsBuilder builder) {
        Pessoa pessoa = new Pessoa(dto);
        service.create(pessoa);
        URI uri = builder.buildAndExpand(pessoa).toUri();
        return ResponseEntity.created(uri).body(new PessoaDetalhamentoDto(pessoa));
    }
}
