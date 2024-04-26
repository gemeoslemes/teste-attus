package br.com.api.testeattus.controllers;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.records.EnderecoAtualizaDto;
import br.com.api.testeattus.records.EnderecoDetalhamentoDto;
import br.com.api.testeattus.records.EnderecoDto;
import br.com.api.testeattus.services.EnderecosService;
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
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecosService service;

    @PostMapping
    public ResponseEntity<EnderecoDetalhamentoDto> create(@Valid @RequestBody EnderecoDto dto, UriComponentsBuilder builder) {
        Enderecos endereco = new Enderecos(dto);
        service.save(endereco);
        URI uri = builder.buildAndExpand(endereco).toUri();
        return ResponseEntity.created(uri).body(new EnderecoDetalhamentoDto(endereco));
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<List<EnderecoDetalhamentoDto>> listingAllAddressesPerPerson(@PathVariable Long idPessoa) {
        List<Enderecos> enderecos = service.searchAllAddressesById(idPessoa);
        List<EnderecoDetalhamentoDto> enderecoDetalhamentoDto = enderecos.stream()
                                                            .map(EnderecoDetalhamentoDto::new)
                                                            .collect(Collectors.toList());
        if (enderecoDetalhamentoDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enderecoDetalhamentoDto);
    }

    @GetMapping("/{idPessoa}/{id}")
    public ResponseEntity<EnderecoDetalhamentoDto> listingAddressByPerson(@PathVariable Long idPessoa, @PathVariable Long id) {
        Enderecos enderecos = service.findAddressesByPersonId(idPessoa, id);
        if(enderecos.getId() != null || enderecos.getPessoas().getId() != null) {
            return ResponseEntity.ok(new EnderecoDetalhamentoDto(enderecos));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDetalhamentoDto> updateEndereco(@PathVariable Long id, @RequestBody EnderecoAtualizaDto dto) {
        Optional<Enderecos> enderecoById = service.findById(id);
        if(enderecoById.isPresent()) {
            Enderecos enderecos = enderecoById.get();
            enderecos.updateData(dto);
            service.update(enderecos);
            return ResponseEntity.ok(new EnderecoDetalhamentoDto(enderecos));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
