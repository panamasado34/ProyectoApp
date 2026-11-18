package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByEstudianteOrderByIdAsc(String estudiante);

    List<Registro> findByBitacoraIdOrderByIdAsc(Long bitacoraId);

    List<Registro> findByEstudianteAndBitacoraIdOrderByIdAsc(String estudiante, Long bitacoraId);
}
