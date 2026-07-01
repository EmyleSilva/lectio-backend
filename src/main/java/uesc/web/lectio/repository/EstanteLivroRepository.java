package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.EstanteLivro;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EstanteLivroRepository extends JpaRepository<EstanteLivro, Long> {
    Optional<EstanteLivro> findByIdAndEstanteId(Long id, Long estanteId);

    boolean existsByEstanteIdAndLivroId(Long estanteId, Long id);

    List<EstanteLivro> findByEstanteId(Long estanteId);
}
