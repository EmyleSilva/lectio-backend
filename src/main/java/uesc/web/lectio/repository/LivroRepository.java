package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Livro;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
}
