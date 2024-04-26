package br.com.api.testeattus.repository;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.enuns.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Enderecos, Long> {
    @Query("SELECT e FROM enderecos e WHERE e.pessoas.id = :idPessoa ORDER BY e.principal DESC")
    List<Enderecos> findAllByIdPerson(Long idPessoa);

    @Query("SELECT e FROM enderecos e WHERE e.principal = true AND e.pessoas.id = :idPessoa")
    List<Enderecos> searchFavoriteAddress(Long idPessoa);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM enderecos e WHERE "
            + "e.logradouro = :logradouro AND "
            + "e.cep = :cep AND "
            + "e.numero = :numero AND "
            + "e.cidade = :cidade AND "
            + "e.estado = :estado AND "
            + "e.pessoas.id = :idPessoa")
    boolean existsByLogradouroAndCepAndNumeroAndCidadeAndEstadoAndPessoas(String logradouro,
                                                                          int cep,
                                                                          int numero,
                                                                          String cidade,
                                                                          Estado estado,
                                                                          Long idPessoa);

    @Query("SELECT e FROM enderecos e WHERE e.pessoas.id = :idPessoa AND e.id = :id")
    Enderecos findAddressesByPersonId(Long idPessoa, Long id);
}

