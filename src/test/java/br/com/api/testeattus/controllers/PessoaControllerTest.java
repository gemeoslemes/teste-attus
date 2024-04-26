package br.com.api.testeattus.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.records.PessoaAtualizaDto;
import br.com.api.testeattus.records.PessoaDto;
import br.com.api.testeattus.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PessoaService pessoaService;

    @Test
    @DisplayName("Teste de criação de pessoa com sucesso")
    void createCase1() throws Exception {
        PessoaDto pessoaDto = new PessoaDto("Victor Lemes", LocalDate.of(2000, 11, 21));

        String pessoaJson = objectMapper.writeValueAsString(pessoaDto);

        when(pessoaService.save(any(Pessoa.class))).thenReturn(new Pessoa());

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Teste de criação de pessoa onde o argumento não foi passado")
    void createCase2() throws Exception {
        PessoaDto pessoaDto = new PessoaDto(null, null);

        String pessoaJson = objectMapper.writeValueAsString(pessoaDto);

        when(pessoaService.save(any(Pessoa.class))).thenReturn(new Pessoa());

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste listando todas as pessoas com sucesso")
    void listOfAllPeopleCase1() throws Exception{
        PessoaDto pessoaDto1 = new PessoaDto("Victor Lemes", LocalDate.of(2000, 11, 21));
        PessoaDto pessoaDto2 = new PessoaDto("Pedro Augusto", LocalDate.of(1993, 10, 21));

        List<Pessoa> pessoas = Arrays.asList(new Pessoa(pessoaDto1), new Pessoa(pessoaDto2));
        when(pessoaService.listAll()).thenReturn(pessoas);

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pessoas.size())))
                .andExpect(jsonPath("$[0].nomeCompleto", is("Victor Lemes")))
                .andExpect(jsonPath("$[0].dataNascimento", is("2000-11-21")))
                .andExpect(jsonPath("$[1].nomeCompleto", is("Pedro Augusto")))
                .andExpect(jsonPath("$[1].dataNascimento", is("1993-10-21")));
    }

    @Test
    @DisplayName("Teste quando a lista estiver vazia")
    void listOfAllPeopleCase2() throws Exception{
        List<Pessoa> pessoas = Collections.emptyList();
        when(pessoaService.listAll()).thenReturn(pessoas);

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Teste para exibir pessoa por ID com sucesso")
    void showPeopleCase1() throws Exception {
        Long existingId = 1L;
        Pessoa existingPerson = new Pessoa();
        existingPerson.setId(existingId);
        existingPerson.setNomeCompleto("Victor");
        existingPerson.setDataNascimento(LocalDate.of(1990, 1, 1));
        when(pessoaService.findById(existingId)).thenReturn(Optional.of(existingPerson));
        mockMvc.perform(get("/pessoas/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPerson.getId()))
                .andExpect(jsonPath("$.nomeCompleto").value(existingPerson.getNomeCompleto()))
                .andExpect(jsonPath("$.dataNascimento").value(existingPerson.getDataNascimento().toString()));
    }

    @Test
    @DisplayName("Teste em que a pessoa com o ID não existe")
    void showPeopleCase2() throws Exception {
        Long nonExistingId = 2L;
        when(pessoaService.findById(nonExistingId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/pessoas/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste para Atualizar pessoa com sucesso")
    void editPersonCase1() throws Exception{
        Long existingId = 1L;
        Pessoa existingPerson = new Pessoa();
        existingPerson.setId(existingId);
        existingPerson.setNomeCompleto("Victor");
        existingPerson.setDataNascimento(LocalDate.of(1990, 1, 1));
        when(pessoaService.findById(existingId)).thenReturn(Optional.of(existingPerson));

        PessoaAtualizaDto updatedDto = new PessoaAtualizaDto("Victor Lemes", null);

        mockMvc.perform(put("/pessoas/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value(updatedDto.nome()));
    }

    @Test
    @DisplayName("Teste em que a pessoa com o ID não existe")
    void editPersonCase2() throws Exception {
        Long nonExistingId = 2L;
        when(pessoaService.findById(nonExistingId)).thenReturn(Optional.empty());

        PessoaAtualizaDto updatedDto = new PessoaAtualizaDto("Victor Lemes", null);

        mockMvc.perform(put("/pessoas/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isNotFound());
    }
}