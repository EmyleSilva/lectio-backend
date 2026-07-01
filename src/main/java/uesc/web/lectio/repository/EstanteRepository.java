package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Estante;

import java.util.List;
import java.util.Optional;

public interface EstanteRepository extends JpaRepository <Estante, Long> {
    Optional<Estante> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Estante> findByUsuarioId(Long usuarioId);
}
