package br.com.api.testeattus.records;

import br.com.api.testeattus.domain.Enderecos;
import br.com.api.testeattus.domain.Pessoa;
import br.com.api.testeattus.enuns.Estado;

public record EnderecoDetalhamentoDto(
        Long id,
        String logradouro,
        Integer cep,
        Integer numero,
        String cidade,
        Estado estado,
        Long pessoa,
        boolean principal
) {
    public EnderecoDetalhamentoDto(Enderecos endereco) {
        this(endereco.getId(), endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade(),
            endereco.getEstado(), endereco.getPessoas().getId(), endereco.isPrincipal());
    }

    public Enderecos toEndereco() {
        Enderecos endereco = new Enderecos();
        endereco.setLogradouro(this.logradouro);
        endereco.setCep(this.cep);
        endereco.setNumero(this.numero);
        endereco.setCidade(this.cidade);
        endereco.setEstado(this.estado);
        Pessoa pessoa1 = new Pessoa();
        endereco.setPessoas(pessoa1);
        endereco.setPrincipal(this.principal);
        return endereco;
    }
}
