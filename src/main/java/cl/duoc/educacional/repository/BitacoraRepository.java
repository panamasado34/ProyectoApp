package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {

    List<Bitacora> findByDocente(String docente);

    List<Bitacora> findByCursoAndEstadoIn(String curso, List<String> estados);

    boolean existsByCursoAndAsignaturaAndMesAndAnio(String curso, String asignatura, String mes, String anio);
}
