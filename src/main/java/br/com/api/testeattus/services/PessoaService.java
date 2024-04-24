package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa save(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    public List<Pessoa> listAll() {
        return repository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {
        return repository.findById(id);
    }
}
