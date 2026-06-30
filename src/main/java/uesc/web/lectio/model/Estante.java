package uesc.web.lectio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uesc.web.lectio.model.enums.TipoEstante;

import java.time.LocalDateTime;

@Entity
@Table(name = "estantes")
@Getter
@Setter
@NoArgsConstructor
public class Estante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(length = 80, nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoEstante tipo = TipoEstante.CUSTOMIZADA;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
}
