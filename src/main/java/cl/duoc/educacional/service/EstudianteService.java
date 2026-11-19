package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    // 1. Listar todos
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    // 2. Listar por curso
    public List<Estudiante> buscarPorCurso(String curso) {
        return estudianteRepository.findByCurso(curso);
    }

    // 3. Buscar por ID
    public Estudiante buscarPorId(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    // 4. Registrar (Crear)
    public Estudiante registrar(Estudiante estudiante) {
        Utilidades.requerido(estudiante.getUsuario(), "usuario");
        Utilidades.requerido(estudiante.getPassword(), "password");
        Utilidades.requerido(estudiante.getNombre(), "nombre");
        Utilidades.requerido(estudiante.getCurso(), "curso");
        if (estudianteRepository.existsByUsuario(estudiante.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        estudiante.setId(null);
        return estudianteRepository.save(estudiante);
    }

    // 5. Borrar
    public boolean borrar(Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 6. Actualizar
    public Estudiante actualizar(Long id, Estudiante detalles) {
        return estudianteRepository.findById(id).map(estudiante -> {
            estudiante.setNombre(Utilidades.requerido(detalles.getNombre(), "nombre"));
            estudiante.setCurso(Utilidades.requerido(detalles.getCurso(), "curso"));
            if (detalles.getPassword() != null && !detalles.getPassword().isBlank()) {
                estudiante.setPassword(detalles.getPassword());
            }
            return estudianteRepository.save(estudiante);
        }).orElse(null);
    }
}
