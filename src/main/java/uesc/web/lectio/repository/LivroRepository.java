package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
