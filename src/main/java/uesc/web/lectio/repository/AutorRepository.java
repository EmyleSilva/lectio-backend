package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
