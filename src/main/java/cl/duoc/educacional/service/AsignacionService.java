package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Asignacion;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.repository.AsignacionRepository;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // 1. Listar todas
    public List<Asignacion> findAll() {
        return asignacionRepository.findAll();
    }

    // 2. Cursos y asignaturas de un docente
    public List<Asignacion> listarPorDocente(String docente) {
        return asignacionRepository.findByDocente(docente);
    }

    // 3. Asignaturas que ve un estudiante (las de su curso)
    public List<Asignacion> listarPorEstudiante(String usuario) {
        Estudiante e = estudianteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no existe"));
        return asignacionRepository.findByCurso(e.getCurso());
    }

    // 4. Registrar (Crear)
    public Asignacion registrar(Asignacion asignacion) {
        Utilidades.requerido(asignacion.getDocente(), "docente");
        Utilidades.requerido(asignacion.getCurso(), "curso");
        Utilidades.requerido(asignacion.getAsignatura(), "asignatura");
        if (asignacionRepository.existsByDocenteAndCursoAndAsignatura(
                asignacion.getDocente(), asignacion.getCurso(), asignacion.getAsignatura())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La asignacion ya existe");
        }
        asignacion.setId(null);
        return asignacionRepository.save(asignacion);
    }

    // 5. Borrar
    public boolean borrar(Long id) {
        if (asignacionRepository.existsById(id)) {
            asignacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
