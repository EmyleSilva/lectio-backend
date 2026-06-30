package uesc.web.lectio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "livros")
@Getter
@Setter
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @Column(length = 20, unique = true)
    private String isbn;

    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @Column(length = 40)
    private String idioma;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(name = "capa_url", length = 255)
    private String capaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editora_id")
    private Editora editora;

    /** Dono do relacionamento N:N -> define a tabela associativa livro_autores */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "livro_autores",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    /** Dono do relacionamento N:N -> define a tabela associativa livro_generos */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "livro_generos",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos = new HashSet<>();

    // Métodos helper para manter os dois lados do relacionamento sincronizados
    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLivros().add(this);
    }

    public void adicionarGenero(Genero genero) {
        this.generos.add(genero);
    }
}
