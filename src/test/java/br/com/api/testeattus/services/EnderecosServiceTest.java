package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;
import br.com.api.testeattus.exceptions.AddressNotFoundException;
import br.com.api.testeattus.exceptions.EnderecoDuplicadoException;
import br.com.api.testeattus.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class EnderecosServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Autowired
    @InjectMocks
    private EnderecosService enderecosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Criando Endereco com sucesso")
    void saveCase1() {
        Enderecos endereco = new Enderecos();
        endereco.setLogradouro("Rua Teste");
        endereco.setCep(12345678);
        endereco.setNumero(123);
        endereco.setCidade("Cidade Teste");
        endereco.setEstado(Estado.SP);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        endereco.setPessoas(pessoa);

        when(repository.existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPessoas().getId())).thenReturn(false);

        when(repository.save(endereco)).thenReturn(endereco);

        Enderecos savedEndereco = enderecosService.save(endereco);

        assertNotNull(savedEndereco);
        assertEquals(endereco, savedEndereco);

        verify(repository, times(1)).existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                endereco.getLogradouro(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPessoas().getId());

        verify(repository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("Capturando Exception de endereço Duplicado")
    void saveCase2() {
        Enderecos endereco1 = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);
        Enderecos endereco2 = createEndereco(2L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);

        when(repository.existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                endereco1.getLogradouro(),
                endereco1.getCep(),
                endereco1.getNumero(),
                endereco1.getCidade(),
                endereco1.getEstado(),
                endereco1.getPessoas().getId())).thenReturn(true);

        assertThrows(EnderecoDuplicadoException.class, () -> {
            enderecosService.save(endereco2);
        });

        verify(repository, never()).save(endereco2);

        verify(repository, times(1)).existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                endereco2.getLogradouro(),
                endereco2.getCep(),
                endereco2.getNumero(),
                endereco2.getCidade(),
                endereco2.getEstado(),
                endereco2.getPessoas().getId());
    }

    @Test
    @DisplayName("Buscando endereços pelo id da pessoa")
    void searchAllAddressesByIdCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Enderecos endereco1 = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);
        Enderecos endereco2 = createEndereco(2L, "Rua Teste 2", 12345674, 153, "Cidade Teste", Estado.CE, 1L);

        when(repository.findAllByIdPerson(pessoa.getId())).thenReturn(List.of(endereco1, endereco2));

        List<Enderecos> returnedAddresses = enderecosService.searchAllAddressesById(pessoa.getId());

        assertThat(returnedAddresses).containsExactlyInAnyOrder(endereco1, endereco2);

        verify(repository, times(1)).findAllByIdPerson(pessoa.getId());
    }

    @Test
    @DisplayName("Endereço não encontrado")
    void searchAllAddressesByIdCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(2L);

        Enderecos endereco1 = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);
        Enderecos endereco2 = createEndereco(2L, "Rua Teste 2", 12345674, 153, "Cidade Teste", Estado.CE, 1L);

        when(repository.findAllByIdPerson(pessoa.getId())).thenReturn(Collections.emptyList());

        assertThrows(AddressNotFoundException.class, () -> {
            List<Enderecos> returnedAddresses = enderecosService.searchAllAddressesById(pessoa.getId());
        });

        verify(repository, times(1)).findAllByIdPerson(pessoa.getId());
    }

    @Test
    @DisplayName("Buscando por id de Pessoa e Endereço")
    void findAddressesByPersonIdCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        Long id = 1L;

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);

        when(repository.findAddressesByPersonId(pessoa.getId(), id)).thenReturn(endereco);

        Enderecos result = enderecosService.findAddressesByPersonId(pessoa.getId(), id);

        assertEquals(endereco, result);
        assertNotNull(result);

        verify(repository, times(1)).findAddressesByPersonId(pessoa.getId(), id);
    }

    @Test
    @DisplayName("Não encontrando pelo id de Pessoa e Endereço")
    void findAddressesByPersonIdCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        Long id = 4L;

        when(repository.findAddressesByPersonId(pessoa.getId(), id)).thenReturn(null);

        assertThrows(AddressNotFoundException.class, () -> {
            enderecosService.findAddressesByPersonId(pessoa.getId(), id);
        });

        verify(repository, times(1)).findAddressesByPersonId(pessoa.getId(), id);
    }

    @Test
    @DisplayName("Verifica se o novo endereço é definido como principal quando não existem endereços favoritos")
    void checkAndSetNewAddressAsNotFavoriteCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Enderecos endereco = new Enderecos();
        endereco.setId(1L);
        endereco.setPessoas(pessoa);
        endereco.setPrincipal(true);

        when(repository.searchFavoriteAddress(pessoa.getId())).thenReturn(Collections.emptyList());

        Enderecos result = enderecosService.checkAndSetNewAddressAsNotFavorite(endereco);

        assertTrue(result.isPrincipal());

        verify(repository, times(1)).searchFavoriteAddress(pessoa.getId());
    }

    @Test
    @DisplayName("Verifica se o novo endereço é definido como principal quando existem endereços favoritos")
    void checkAndSetNewAddressAsNotFavoriteCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        Enderecos endereco = new Enderecos();
        endereco.setId(1L);
        endereco.setPessoas(pessoa);
        endereco.setPrincipal(true);

        Enderecos endereco2 = new Enderecos();
        endereco2.setId(1L);
        endereco2.setPessoas(pessoa);
        endereco2.setPrincipal(true);

        when(repository.searchFavoriteAddress(pessoa.getId())).thenReturn(Collections.singletonList(endereco));

        Enderecos result = enderecosService.checkAndSetNewAddressAsNotFavorite(endereco2);

        assertFalse(result.isPrincipal());

        verify(repository, times(1)).searchFavoriteAddress(pessoa.getId());
    }

    private Enderecos createEndereco(Long id, String logradouro, int cep, int numero, String cidade, Estado estado, Long pessoaId) {
        Enderecos endereco = new Enderecos();
        endereco.setId(id);
        endereco.setLogradouro(logradouro);
        endereco.setCep(cep);
        endereco.setNumero(numero);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        endereco.setPessoas(pessoa);
        return endereco;
    }
}