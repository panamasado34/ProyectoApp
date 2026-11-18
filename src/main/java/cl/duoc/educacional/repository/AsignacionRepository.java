package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByDocente(String docente);

    List<Asignacion> findByCurso(String curso);

    boolean existsByDocenteAndCursoAndAsignatura(String docente, String curso, String asignatura);
}
