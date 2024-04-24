package br.com.api.testeattus.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "pessoas")
@Table(name = "pessoas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nomeCompleto;
    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "enderecos_id")
    @OneToMany(mappedBy = "pessoas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enderecos> enderecos;
}
