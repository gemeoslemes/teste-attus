package br.com.api.testeattus.controllers;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.records.PessoaAtualizaDto;
import br.com.api.testeattus.records.PessoaDetalhamentoDto;
import br.com.api.testeattus.records.PessoaDto;
import br.com.api.testeattus.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @PostMapping
    public ResponseEntity<PessoaDetalhamentoDto> create(@RequestBody @Valid PessoaDto dto, UriComponentsBuilder builder) {
        Pessoa pessoa = new Pessoa(dto);
        service.save(pessoa);
        URI uri = builder.buildAndExpand(pessoa).toUri();
        return ResponseEntity.created(uri).body(new PessoaDetalhamentoDto(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<PessoaDetalhamentoDto>> listOfAllPeople() {
        List<Pessoa> pessoas = service.listAll();
        List<PessoaDetalhamentoDto> pessoasDto = pessoas.stream()
                .map(PessoaDetalhamentoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(pessoasDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDetalhamentoDto> showPeople(@PathVariable Long id) {
        Optional<Pessoa> pessoa = service.findById(id);
        if (pessoa.isPresent()) {
            PessoaDetalhamentoDto pessoaDto = new PessoaDetalhamentoDto(pessoa.get());
            return ResponseEntity.ok(pessoaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDetalhamentoDto> editPerson(@PathVariable Long id, @RequestBody PessoaAtualizaDto pessoaDto) {
        Optional<Pessoa> pessoaById = service.findById(id);
        if(pessoaById.isPresent()) {
            Pessoa pessoa = pessoaById.get();
            pessoa.updateData(pessoaDto);
            service.save(pessoa);
            PessoaDetalhamentoDto pessoaDetalhamentoDto = new PessoaDetalhamentoDto(pessoa);
            return ResponseEntity.ok(pessoaDetalhamentoDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
