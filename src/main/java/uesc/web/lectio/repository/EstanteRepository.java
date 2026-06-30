package uesc.web.lectio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uesc.web.lectio.model.Estante;

public interface EstanteRepository extends JpaRepository <Estante, Long> {
}
