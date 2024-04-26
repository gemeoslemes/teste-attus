package br.com.api.testeattus.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;
import br.com.api.testeattus.records.EnderecoAtualizaDto;
import br.com.api.testeattus.records.EnderecoDetalhamentoDto;
import br.com.api.testeattus.records.EnderecoDto;
import br.com.api.testeattus.services.EnderecosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnderecosService enderecosService;

    @Test
    @DisplayName("Criando endereco com sucesso")
    void createCase1() throws Exception {
        EnderecoDto enderecoDto = new EnderecoDto("Rua 1", 22333457, 102,"Campinas", Estado.SP, 1L, true);
        String enderecoJson = objectMapper.writeValueAsString(enderecoDto);

        when(enderecosService.save(any(Enderecos.class))).thenReturn(new Enderecos());

        mockMvc.perform(post("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enderecoJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Teste não criando o endereço por argumentos inválidos")
    void createCase2() throws Exception {
        EnderecoDto enderecoDto = new EnderecoDto("Rua 1", 22333457, null,"", Estado.SP, 1L, true);
        String enderecoJson = objectMapper.writeValueAsString(enderecoDto);

        when(enderecosService.save(any(Enderecos.class))).thenReturn(new Enderecos());

        mockMvc.perform(post("/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enderecoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Listando endereços por id de Pessoa com sucesso")
    void listingAllAddressesPerPersonCase1() throws Exception {
        Long idPessoa = 1L;
        EnderecoDetalhamentoDto enderecoDetalhamentoDto1 = new EnderecoDetalhamentoDto(1L, "Rua 1", 123456, 101, "Campinas", Estado.SP, idPessoa, true);
        EnderecoDetalhamentoDto enderecoDetalhamentoDto2 = new EnderecoDetalhamentoDto(2L, "Rua 2", 654321, 202, "São Paulo", Estado.SP, idPessoa, false);
        List<EnderecoDetalhamentoDto> enderecosDto = Arrays.asList(enderecoDetalhamentoDto1, enderecoDetalhamentoDto2);

        when(enderecosService.searchAllAddressesById(idPessoa)).thenReturn(enderecosDto.stream()
                .map(EnderecoDetalhamentoDto::toEndereco)
                .collect(Collectors.toList()));

        mockMvc.perform(get("/enderecos/{idPessoa}", idPessoa))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(enderecosDto.size())))
                .andExpect(jsonPath("$[0].logradouro", is(enderecoDetalhamentoDto1.logradouro())))
                .andExpect(jsonPath("$[0].cep", is(enderecoDetalhamentoDto1.cep())))
                .andExpect(jsonPath("$[0].numero", is(enderecoDetalhamentoDto1.numero())))
                .andExpect(jsonPath("$[0].cidade", is(enderecoDetalhamentoDto1.cidade())))
                .andExpect(jsonPath("$[0].estado", is(enderecoDetalhamentoDto1.estado().name())))
                .andExpect(jsonPath("$[0].principal", is(enderecoDetalhamentoDto1.principal())))
                .andExpect(jsonPath("$[1].logradouro", is(enderecoDetalhamentoDto2.logradouro())))
                .andExpect(jsonPath("$[1].cep", is(enderecoDetalhamentoDto2.cep())))
                .andExpect(jsonPath("$[1].numero", is(enderecoDetalhamentoDto2.numero())))
                .andExpect(jsonPath("$[1].cidade", is(enderecoDetalhamentoDto2.cidade())))
                .andExpect(jsonPath("$[1].estado", is(enderecoDetalhamentoDto2.estado().name())))
                .andExpect(jsonPath("$[1].principal", is(enderecoDetalhamentoDto2.principal())));
    }

    @Test
    @DisplayName("Teste de busca por id de Pessoa inexistente")
    void listingAllAddressesPerPersonCase2() throws Exception {
        Long idPessoa = 1L;
        EnderecoDetalhamentoDto enderecoDetalhamentoDto2 = new EnderecoDetalhamentoDto(2L, "Rua 2", 654321, 202, "São Paulo", Estado.SP, 2L, false);

        when(enderecosService.searchAllAddressesById(eq(idPessoa))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/enderecos/{idPessoa}", idPessoa))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste listando por id de Endereço e Pessoa com sucesso")
    void listingAddressByPersonCase1() throws Exception {
        Long idPessoa = 1L;
        Long idEndereco = 1L;

        Enderecos enderecoSimulado = new Enderecos();
        enderecoSimulado.setId(idEndereco);
        enderecoSimulado.setLogradouro("Rua ABC");
        enderecoSimulado.setCep(12345);
        enderecoSimulado.setNumero(101);
        enderecoSimulado.setCidade("Cidade");
        enderecoSimulado.setEstado(Estado.SP);
        enderecoSimulado.setPessoas(new Pessoa());
        enderecoSimulado.setPrincipal(true);

        when(enderecosService.findAddressesByPersonId(idPessoa, idEndereco)).thenReturn(enderecoSimulado);

        mockMvc.perform(get("/enderecos/{idPessoa}/{idEndereco}", idPessoa, idEndereco))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logradouro").value(enderecoSimulado.getLogradouro()))
                .andExpect(jsonPath("$.cep").value(enderecoSimulado.getCep()))
                .andExpect(jsonPath("$.numero").value(enderecoSimulado.getNumero()))
                .andExpect(jsonPath("$.cidade").value(enderecoSimulado.getCidade()))
                .andExpect(jsonPath("$.estado").value(enderecoSimulado.getEstado().name()))
                .andExpect(jsonPath("$.principal").value(enderecoSimulado.isPrincipal()));
    }

    @Test
    @DisplayName("Teste de falha pelo id de Pessoa e pelo id de Endeço")
    void listingAddressByPersonCase2() throws Exception {
        Long idPessoa = 1L;
        Long idEndereco = 1L;

        EnderecoDetalhamentoDto enderecoDetalhamentoDto = new EnderecoDetalhamentoDto(idEndereco, "Rua ABC", 12345, 101, "Cidade", Estado.SP, 5L, true);

        when(enderecosService.findAddressesByPersonId(idPessoa, idEndereco)).thenReturn(enderecoDetalhamentoDto.toEndereco());

        mockMvc.perform(get("/enderecos/{idPessoa}/{idEndereco}", idPessoa, idEndereco))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de atualização de endereço com ID válido")
    void updateEnderecoCase1() throws Exception {
        Long idEndereco = 1L;
        Pessoa pessoa = new Pessoa();

        EnderecoAtualizaDto dto = new EnderecoAtualizaDto("Nova Rua", 54321, 202, "Rio de Janeiro",Estado.RJ, false);

        Enderecos enderecoExistente = new Enderecos();
        enderecoExistente.setId(idEndereco);
        enderecoExistente.setPessoas(pessoa);

        when(enderecosService.findById(idEndereco)).thenReturn(Optional.of(enderecoExistente));

        mockMvc.perform(put("/enderecos/{id}", idEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logradouro").value(dto.logradouro()))
                .andExpect(jsonPath("$.cep").value(dto.cep()))
                .andExpect(jsonPath("$.numero").value(dto.numero()))
                .andExpect(jsonPath("$.cidade").value(dto.cidade()))
                .andExpect(jsonPath("$.estado").value(dto.estado().name()))
                .andExpect(jsonPath("$.principal").value(dto.principal()));
    }

    @Test
    @DisplayName("Teste ID não encontrado para atualizar")
    void updateEnderecoCase2() throws Exception {
        Long idEndereco = 1L;

        EnderecoAtualizaDto dto = new EnderecoAtualizaDto("Nova Rua", 54321, 202, "Rio de Janeiro",Estado.RJ, false);

        Enderecos enderecoExistente = new Enderecos();
        enderecoExistente.setId(idEndereco);

        when(enderecosService.findById(idEndereco)).thenReturn(Optional.empty());

        mockMvc.perform(put("/enderecos/{id}", idEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}