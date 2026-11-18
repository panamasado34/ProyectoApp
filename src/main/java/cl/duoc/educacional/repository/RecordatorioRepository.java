package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {

    List<Recordatorio> findByDocente(String docente);
}
