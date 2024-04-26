package br.com.api.testeattus.repository;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class EnderecoRepositoryTest {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Buscando todos os endereços pelo id de pessoa com sucesso")
    void findAllByIdPersonCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 05, 21));

        Pessoa pessoaGerenciada = entityManager.merge(pessoa);

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, pessoaGerenciada.getId());

        entityManager.merge(endereco);

        List<Enderecos> enderecosBuscadoPorPessoa = enderecoRepository.findAllByIdPerson(pessoaGerenciada.getId());

        assertThat(enderecosBuscadoPorPessoa).isNotEmpty();
    }

    @Test()
    @DisplayName("Não encontrando o id de Pessoa e não expondo os endereços")
    void findAllByIdPersonCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(999L);
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 05, 21));

        createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);

        List<Enderecos> enderecosBuscadoPorPessoa = enderecoRepository.findAllByIdPerson(pessoa.getId());

        assertThat(enderecosBuscadoPorPessoa).isEmpty();
    }

    @Test
    @DisplayName("Encontrando endereço principal de uma pessoa com sucesso")
    void searchFavoriteAddressCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 05, 21));

        Pessoa pessoaGerenciada = entityManager.merge(pessoa);

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, pessoaGerenciada.getId());
        endereco.setPrincipal(true);

        entityManager.merge(endereco);

        List<Enderecos> enderecoFavorito = enderecoRepository.searchFavoriteAddress(pessoaGerenciada.getId());

        assertThat(enderecoFavorito).isNotEmpty();
    }

    @Test
    @DisplayName("Não encontrando endereço principal de uma pessoa")
    void searchFavoriteAddressCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(999L);
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 05, 21));

        Pessoa pessoaGerenciada = entityManager.merge(pessoa);

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, 1L);
        endereco.setPrincipal(false);

        List<Enderecos> enderecoFavorito = enderecoRepository.searchFavoriteAddress(pessoaGerenciada.getId());

        assertThat(enderecoFavorito).isEmpty();
    }

    @Test
    @DisplayName("Verificar se um endereço existe para uma pessoa específica")
    void testExistsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoasCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 05, 21));

        Pessoa pessoaGerenciada = entityManager.merge(pessoa);

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, pessoaGerenciada.getId());
        endereco.setPrincipal(true);

        entityManager.merge(endereco);

        String logradouro = "Rua Teste";
        int cep = 12345678;
        int numero = 123;
        String cidade = "Cidade Teste";
        Estado estado = Estado.MG;
        Long idPessoa = pessoaGerenciada.getId();

        boolean enderecoExiste = enderecoRepository.existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(
                logradouro, cep, numero, cidade, estado, idPessoa);

        assertThat(enderecoExiste).isTrue();
    }

    @Test
    @DisplayName("Encontrar endereço por ID da pessoa e ID do endereço")
    void testFindAddressesByPersonIdCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNomeCompleto("Gustavo");
        pessoa.setDataNascimento(LocalDate.of(2000, 5, 21));

        Pessoa pessoaGerenciada = entityManager.merge(pessoa);

        Enderecos endereco = createEndereco(1L, "Rua Teste", 12345678, 123, "Cidade Teste", Estado.MG, pessoaGerenciada.getId());
        Enderecos enderecoGerenciado = entityManager.merge(endereco);

        Enderecos enderecoEncontrado = enderecoRepository.findAddressesByPersonId(pessoaGerenciada.getId(), enderecoGerenciado.getId());

        assertThat(enderecoEncontrado).isNotNull();
        assertThat(enderecoEncontrado.getId()).isEqualTo(enderecoGerenciado.getId());
        assertThat(enderecoEncontrado.getLogradouro()).isEqualTo(enderecoGerenciado.getLogradouro());
        assertThat(enderecoEncontrado.getCep()).isEqualTo(enderecoGerenciado.getCep());
        assertThat(enderecoEncontrado.getNumero()).isEqualTo(enderecoGerenciado.getNumero());
        assertThat(enderecoEncontrado.getCidade()).isEqualTo(enderecoGerenciado.getCidade());
        assertThat(enderecoEncontrado.getEstado()).isEqualTo(enderecoGerenciado.getEstado());
        assertThat(enderecoEncontrado.getPessoas().getId()).isEqualTo(pessoaGerenciada.getId());
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