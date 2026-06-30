package uesc.web.lectio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
@Getter
@Setter
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 160, nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(length = 80)
    private String nacionalidade;

    /** Lado inverso do ManyToMany*/
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros = new HashSet<>();
}
