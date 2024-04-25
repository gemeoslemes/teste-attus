package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.exceptions.AddressNotFoundException;
import br.com.api.testeattus.exceptions.EnderecoDuplicadoException;
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
            throw new EnderecoDuplicadoException("Endereço já existe");
        }
    }

    public List<Enderecos> searchAllAddressesById(Long idPessoa) {
        List<Enderecos> allByIdAddresses = repository.findAllByIdPerson(idPessoa);
        if(!allByIdAddresses.isEmpty()){
            return allByIdAddresses;
        }
        throw new AddressNotFoundException("Endereço não encontrado!");
    }

    public Enderecos findAddressesByPersonId(Long idPessoa, Long id) {
        if(repository.findAddressesByPersonId(idPessoa, id) == null) {
            throw new AddressNotFoundException("Endereço não encontrado!");
        } else {
            return repository.findAddressesByPersonId(idPessoa, id);
        }
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
