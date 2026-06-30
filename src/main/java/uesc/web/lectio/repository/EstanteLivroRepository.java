package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.EstanteLivro;

public interface EstanteLivroRepository extends JpaRepository<EstanteLivro, Long> {
}
