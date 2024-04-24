package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa create(Pessoa pessoa) {
        return repository.save(pessoa);
    }
}
