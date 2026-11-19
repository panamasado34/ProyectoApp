package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Bitacora;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.model.Registro;
import cl.duoc.educacional.repository.BitacoraRepository;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.repository.RegistroRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RegistroService {
    // Estados de un registro: borrador -> pendiente (enviado al docente) -> revisado
    public static final String BORRADOR = "borrador";
    public static final String PENDIENTE = "pendiente";
    public static final String REVISADO = "revisado";

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // ====================== ESTUDIANTE ======================

    // 1. Crear registro (queda como borrador)
    public Registro crear(Registro registro) {
        Utilidades.requerido(registro.getEstudiante(), "estudiante");
        Utilidades.requerido(registro.getFechaClase(), "fechaClase");
        Utilidades.requerido(registro.getContenido(), "contenido");
        Utilidades.requerido(registro.getActividad(), "actividad");
        Utilidades.requerido(registro.getAprendizaje(), "aprendizaje");
        if (registro.getBitacoraId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'bitacoraId' es obligatorio");
        }

        Bitacora bitacora = bitacoraRepository.findById(registro.getBitacoraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bitacora no existe"));
        Estudiante estudiante = estudianteRepository.findByUsuario(registro.getEstudiante())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no existe"));

        if (!bitacora.getCurso().equals(estudiante.getCurso())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La bitacora no corresponde a tu curso");
        }
        if (!BitacoraService.PUBLICADA.equals(bitacora.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La bitacora no esta publicada");
        }

        registro.setId(null);
        registro.setEstado(BORRADOR);
        registro.setFechaCreacion(Utilidades.fechaHoy());
        registro.setFechaEnvio("");
        registro.setComentario("");
        registro.setFechaRetroalimentacion("");
        registro.setDocenteRevisor("");
        limpiarOpcionales(registro);
        return registroRepository.save(registro);
    }

    // 2. Editar registro (solo mientras sea borrador)
    public Registro actualizar(Long id, Registro datos, String estudiante) {
        Registro r = obtenerPropio(id, estudiante);
        if (!BORRADOR.equals(r.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede editar un registro en borrador");
        }
        r.setFechaClase(Utilidades.requerido(datos.getFechaClase(), "fechaClase"));
        r.setContenido(Utilidades.requerido(datos.getContenido(), "contenido"));
        r.setActividad(Utilidades.requerido(datos.getActividad(), "actividad"));
        r.setAprendizaje(Utilidades.requerido(datos.getAprendizaje(), "aprendizaje"));
        r.setDudas(Utilidades.vacioSiNulo(datos.getDudas()));
        r.setFotografias(Utilidades.vacioSiNulo(datos.getFotografias()));
        r.setArchivos(Utilidades.vacioSiNulo(datos.getArchivos()));
        r.setEnlaces(Utilidades.vacioSiNulo(datos.getEnlaces()));
        return registroRepository.save(r);
    }

    // 3. Enviar al docente (borrador -> pendiente)
    public Registro enviar(Long id, String estudiante) {
        Registro r = obtenerPropio(id, estudiante);
        if (!BORRADOR.equals(r.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El registro ya fue enviado");
        }
        Bitacora b = bitacoraRepository.findById(r.getBitacoraId()).orElse(null);
        if (b == null || !BitacoraService.PUBLICADA.equals(b.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La bitacora ya no esta publicada");
        }
        r.setEstado(PENDIENTE);
        r.setFechaEnvio(Utilidades.fechaHoy());
        return registroRepository.save(r);
    }

    // 4. Eliminar (solo borradores propios)
    public void eliminar(Long id, String estudiante) {
        Registro r = obtenerPropio(id, estudiante);
        if (!BORRADOR.equals(r.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede eliminar un borrador");
        }
        registroRepository.deleteById(id);
    }

    // 5. Historial personal (el estudiante solo ve sus propios registros)
    public List<Registro> listarPorEstudiante(String estudiante, Long bitacoraId) {
        if (bitacoraId != null) {
            return registroRepository.findByEstudianteAndBitacoraIdOrderByIdAsc(estudiante, bitacoraId);
        }
        return registroRepository.findByEstudianteOrderByIdAsc(estudiante);
    }

    // ====================== DOCENTE ======================

    // 6. Aportes de una bitacora, con filtros opcionales por estudiante, fecha de clase o estado.
    //    Los borradores del estudiante nunca se muestran al docente.
    public List<Registro> listarParaDocente(Long bitacoraId, String docente,
                                            String estudiante, String fecha, String estado) {
        bitacoraService.obtenerDeDocente(bitacoraId, docente);
        return registroRepository.findByBitacoraIdOrderByIdAsc(bitacoraId).stream()
                .filter(r -> !BORRADOR.equals(r.getEstado()))
                .filter(r -> vacio(estudiante) || r.getEstudiante().equals(estudiante))
                .filter(r -> vacio(fecha) || r.getFechaClase().equals(fecha))
                .filter(r -> vacio(estado) || r.getEstado().equals(estado))
                .toList();
    }

    // 7. Aportes pendientes de revision
    public List<Registro> listarPendientes(Long bitacoraId, String docente) {
        return listarParaDocente(bitacoraId, docente, null, null, PENDIENTE);
    }

    // 8. Retroalimentacion
    public Registro comentar(Long id, String docente, String comentario) {
        Utilidades.requerido(comentario, "comentario");
        Registro r = obtenerParaDocente(id, docente);
        r.setComentario(comentario);
        r.setFechaRetroalimentacion(Utilidades.fechaHoy());
        r.setDocenteRevisor(docente);
        return registroRepository.save(r);
    }

    // 9. Marcar como revisado
    public Registro marcarRevisado(Long id, String docente) {
        Registro r = obtenerParaDocente(id, docente);
        r.setEstado(REVISADO);
        r.setDocenteRevisor(docente);
        return registroRepository.save(r);
    }

    // ====================== CONTROL DE ACCESO ======================

    private Registro obtenerPropio(Long id, String estudiante) {
        Registro r = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no existe"));
        if (estudiante == null || !r.getEstudiante().equals(estudiante)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este registro no es tuyo");
        }
        return r;
    }

    private Registro obtenerParaDocente(Long id, String docente) {
        Registro r = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no existe"));
        bitacoraService.obtenerDeDocente(r.getBitacoraId(), docente);
        if (BORRADOR.equals(r.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El estudiante aun no envia este registro");
        }
        return r;
    }

    private void limpiarOpcionales(Registro r) {
        r.setDudas(Utilidades.vacioSiNulo(r.getDudas()));
        r.setFotografias(Utilidades.vacioSiNulo(r.getFotografias()));
        r.setArchivos(Utilidades.vacioSiNulo(r.getArchivos()));
        r.setEnlaces(Utilidades.vacioSiNulo(r.getEnlaces()));
    }

    private boolean vacio(String s) {
        return s == null || s.isBlank();
    }
}
