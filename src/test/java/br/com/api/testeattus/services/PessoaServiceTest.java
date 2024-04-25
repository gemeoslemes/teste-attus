package br.com.api.testeattus.services;

import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.exceptions.ViolationOfArgumentsException;
import br.com.api.testeattus.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class PessoaServiceTest {
    @Mock
    private PessoaRepository repository;

    @Autowired
    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Criando pessoa com sucesso")
    void saveCase1() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNomeCompleto("Bia");
        pessoa.setDataNascimento(LocalDate.of(2000, 5, 5));

        when(repository.save(pessoa)).thenReturn(pessoa);
        Pessoa savedPessoa = pessoaService.save(pessoa);

        assertNotNull(savedPessoa);
        assertEquals(pessoa.getId(), savedPessoa.getId());
        assertEquals(pessoa.getNomeCompleto(), savedPessoa.getNomeCompleto());
        assertEquals(pessoa.getDataNascimento(), savedPessoa.getDataNascimento());

        verify(repository, times(1)).save(pessoa);
    }

    @Test
    @DisplayName("Falta de Argumento ao criar uma Pessoa")
    void saveCase2() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        assertThrows(ViolationOfArgumentsException.class, () -> {
            pessoaService.save(pessoa);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Listando todas as pessoas com sucesso ")
    void listAllCase1() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNomeCompleto("Bia");
        pessoa1.setDataNascimento(LocalDate.of(2000, 5, 5));

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(2L);
        pessoa2.setNomeCompleto("Gustavo");
        pessoa2.setDataNascimento(LocalDate.of(1994, 5, 10));

        when(repository.save(pessoa1)).thenReturn(pessoa1);
        when(repository.save(pessoa2)).thenReturn(pessoa2);
        when(repository.findAll()).thenReturn(Arrays.asList(pessoa1, pessoa2));

        List<Pessoa> listPessoa = pessoaService.listAll();

        assertEquals(2, listPessoa.size());
        assertTrue(listPessoa.contains(pessoa1));
        assertTrue(listPessoa.contains(pessoa2));

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Lista vazia esperada")
    void listAllCase2() {
        List<Pessoa> pessoas = new ArrayList<>();

        when(repository.findAll()).thenReturn(pessoas);

        List<Pessoa> result = pessoaService.listAll();

        assertTrue(result.isEmpty());

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Buscando pessoa por id com sucesso")
    void findByIdCase1() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        pessoa1.setNomeCompleto("Bia");
        pessoa1.setDataNascimento(LocalDate.of(2000, 5, 5));

        when(repository.save(pessoa1)).thenReturn(pessoa1);
        when(repository.findById(pessoa1.getId())).thenReturn(Optional.of(pessoa1));

        Optional<Pessoa> result = pessoaService.findById(pessoa1.getId());

        assertTrue(result.isPresent());
        assertEquals(pessoa1, result.get());

        verify(repository, times(1)).findById(pessoa1.getId());
    }

    @Test
    @DisplayName("Buscando pessoa que não existe")
    void findByIdCase2() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Pessoa> result = pessoaService.findById(1L);

        assertTrue(result.isEmpty());

        verify(repository, times(1)).findById(1L);
    }
}