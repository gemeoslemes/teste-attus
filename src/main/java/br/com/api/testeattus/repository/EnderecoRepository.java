package br.com.api.testeattus.repository;

import br.com.api.testeattus.domain.Enderecos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Enderecos, Long> {
}
