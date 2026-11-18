package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);

    List<Estudiante> findByCurso(String curso);
}
