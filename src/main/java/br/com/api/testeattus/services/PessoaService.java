package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.exceptions.ViolationOfArgumentsException;
import br.com.api.testeattus.repository.PessoaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa save(Pessoa pessoa) {
        if (pessoa.getNomeCompleto() == null || pessoa.getDataNascimento() == null) {
            throw new ViolationOfArgumentsException();
        }
        return repository.save(pessoa);
    }

    public List<Pessoa> listAll() {
        return repository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {
        return repository.findById(id);
    }
}
