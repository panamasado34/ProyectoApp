package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Bitacora;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.repository.AsignacionRepository;
import cl.duoc.educacional.repository.BitacoraRepository;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BitacoraService {
    // Estados de una bitacora: borrador -> publicada -> cerrada
    public static final String BORRADOR = "borrador";
    public static final String PUBLICADA = "publicada";
    public static final String CERRADA = "cerrada";

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // 1. Bitacoras de un docente (historial completo)
    public List<Bitacora> listarPorDocente(String docente) {
        return bitacoraRepository.findByDocente(docente);
    }

    // 2. Bitacoras visibles para un estudiante: solo de su curso y ya publicadas (o cerradas)
    public List<Bitacora> listarParaEstudiante(String usuario) {
        Estudiante e = estudianteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no existe"));
        return bitacoraRepository.findByCursoAndEstadoIn(e.getCurso(), List.of(PUBLICADA, CERRADA));
    }

    // 3. Buscar por ID
    public Bitacora buscarPorId(Long id) {
        return bitacoraRepository.findById(id).orElse(null);
    }

    // 4. Crear: el docente solo puede crear bitacoras de sus cursos y asignaturas
    public Bitacora crear(Bitacora bitacora) {
        Utilidades.requerido(bitacora.getDocente(), "docente");
        Utilidades.requerido(bitacora.getCurso(), "curso");
        Utilidades.requerido(bitacora.getAsignatura(), "asignatura");
        Utilidades.requerido(bitacora.getMes(), "mes");
        Utilidades.requerido(bitacora.getAnio(), "anio");
        Utilidades.requerido(bitacora.getTitulo(), "titulo");
        Utilidades.requerido(bitacora.getProposito(), "proposito");
        Utilidades.requerido(bitacora.getInstrucciones(), "instrucciones");
        Utilidades.requerido(bitacora.getPreguntas(), "preguntas");

        if (!asignacionRepository.existsByDocenteAndCursoAndAsignatura(
                bitacora.getDocente(), bitacora.getCurso(), bitacora.getAsignatura())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "El docente no tiene asignado ese curso y asignatura");
        }
        if (bitacoraRepository.existsByCursoAndAsignaturaAndMesAndAnio(
                bitacora.getCurso(), bitacora.getAsignatura(), bitacora.getMes(), bitacora.getAnio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una bitacora de ese curso, asignatura, mes y anio");
        }

        bitacora.setId(null);
        bitacora.setEstado(BORRADOR);
        bitacora.setFechaPublicacion("");
        bitacora.setFechaCierre("");
        return bitacoraRepository.save(bitacora);
    }

    // 5. Publicar (borrador -> publicada)
    public Bitacora publicar(Long id, String docente) {
        Bitacora b = obtenerDeDocente(id, docente);
        if (!BORRADOR.equals(b.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede publicar una bitacora en borrador");
        }
        b.setEstado(PUBLICADA);
        b.setFechaPublicacion(Utilidades.fechaHoy());
        return bitacoraRepository.save(b);
    }

    // 6. Cerrar (publicada -> cerrada)
    public Bitacora cerrar(Long id, String docente) {
        Bitacora b = obtenerDeDocente(id, docente);
        if (!PUBLICADA.equals(b.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede cerrar una bitacora publicada");
        }
        b.setEstado(CERRADA);
        b.setFechaCierre(Utilidades.fechaHoy());
        return bitacoraRepository.save(b);
    }

    /** Devuelve la bitacora solo si pertenece al docente (404 si no existe, 403 si es de otro docente). */
    public Bitacora obtenerDeDocente(Long id, String docente) {
        Bitacora b = bitacoraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bitacora no existe"));
        if (docente == null || !b.getDocente().equals(docente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No eres el docente responsable de esta bitacora");
        }
        return b;
    }
}
