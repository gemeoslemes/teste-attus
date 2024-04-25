package br.com.api.testeattus.domain;

import br.com.api.testeattus.enuns.Estado;
import br.com.api.testeattus.records.EnderecoAtualizaDto;
import br.com.api.testeattus.records.EnderecoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "enderecos")
@Table(name = "enderecos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"logradouro", "cep", "numero", "cidade", "estado", "pessoa"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Enderecos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private Integer cep;

    private Integer numero;

    private String cidade;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoas;

    @Column(name = "favorito")
    private boolean favorito;

    public Enderecos(EnderecoDto dto) {
        this.logradouro = dto.logradouro();
        this.cep = dto.cep();
        this.numero = dto.numero();
        this.cidade = dto.cidade();
        this.estado = dto.estado();
        this.pessoas = new Pessoa();
        this.pessoas.setId(dto.pessoa());
        if(dto.favorito() == true) {
            this.favorito = dto.favorito();
        } else {
            this.favorito = false;
        }
    }

    public void updateData(EnderecoAtualizaDto enderecos) {
        if(enderecos.logradouro() != null && !enderecos.logradouro().isEmpty()) {
            this.logradouro = enderecos.logradouro();
        }
        if(enderecos.cep() != null) {
            this.cep = enderecos.cep();
        }
        if(enderecos.numero() != null) {
            this.numero = enderecos.numero();
        }
        if(enderecos.cidade() != null && !enderecos.cidade().isEmpty()) {
            this.cidade = enderecos.cidade();
        }
        if(enderecos.estado() != null) {
            this.estado = enderecos.estado();
        }
        if(enderecos.favorito()) {
            this.favorito = enderecos.favorito();
        }
    }
}
