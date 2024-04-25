package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecosService {

    @Autowired
    private EnderecoRepository repository;

    public Enderecos save(Enderecos endereco) {
        checkAndSetNewAddressAsNotFavorite(endereco);
        if (!repository.existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPessoas().getId())) {

            return repository.save(endereco);
        } else {
            throw new RuntimeException("Endereço já existe");
        }

    }


    public List<Enderecos> searchAllAddressesById(Long idPessoa) {
        return repository.findAllByIdPerson(idPessoa);
    }

    public Enderecos findAddressesByPersonId(Long idPessoa, Long id) {
        return repository.findAddressesByPersonId(idPessoa, id);
    }

    public Optional<Enderecos> findById(Long id) {
        return repository.findById(id);
    }

    public void update(Enderecos enderecos) {
        checkAndSetNewAddressAsNotFavorite(enderecos);
        repository.save(enderecos);
    }

    protected Enderecos checkAndSetNewAddressAsNotFavorite(Enderecos enderecos) {
        List<Enderecos> listEnderecos = repository.searchFavoriteAddress(enderecos.getPessoas().getId());
        if (!listEnderecos.isEmpty()) {
            enderecos.setFavorito(false);
        }
        return enderecos;
    }
}
