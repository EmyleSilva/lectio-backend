package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
