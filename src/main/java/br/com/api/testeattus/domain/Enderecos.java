package br.com.api.testeattus.domain;

import br.com.api.testeattus.enuns.Estado;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "enderecos")
@Table(name = "enderecos")
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

    private int cep;

    private int numero;

    private String cidade;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoas;
}
