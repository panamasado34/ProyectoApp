package cl.duoc.educacional.repository;

import cl.duoc.educacional.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
