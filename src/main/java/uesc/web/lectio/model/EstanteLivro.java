package uesc.web.lectio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uesc.web.lectio.model.enums.StatusLeitura;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "estante_livros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"estante_id", "livro_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class EstanteLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estante_id", nullable = false)
    private Estante estante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_leitura", nullable = false, length = 20)
    private StatusLeitura statusLeitura = StatusLeitura.QUERO_LER;

    @Column(name = "data_adicionado", nullable = false, updatable = false)
    private LocalDateTime dataAdicionado;

    @Column(name = "data_inicio_leitura")
    private LocalDate dataInicioLeitura;

    @Column(name = "data_fim_leitura")
    private LocalDate dataFimLeitura;

    @Column(name = "pagina_atual")
    private Integer paginaAtual = 0;

    @PrePersist
    protected void onCreate() {
        this.dataAdicionado = LocalDateTime.now();
    }
}