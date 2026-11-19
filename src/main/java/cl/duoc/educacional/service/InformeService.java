package cl.duoc.educacional.service;

import cl.duoc.educacional.dto.InformeMensualResponse;
import cl.duoc.educacional.dto.ParticipacionResponse;
import cl.duoc.educacional.model.Bitacora;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.model.Registro;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.repository.RegistroRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InformeService {
    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * Indicadores de participacion. Un estudiante "participo" si envio al menos un registro
     * (los borradores no cuentan como aporte).
     */
    public ParticipacionResponse participacion(Long bitacoraId, String docente) {
        Bitacora b = bitacoraService.obtenerDeDocente(bitacoraId, docente);
        return calcular(b, enviados(bitacoraId));
    }

    /** Datos del informe mensual (la app arma el PDF con esto). */
    public InformeMensualResponse informe(Long bitacoraId, String docente) {
        Bitacora b = bitacoraService.obtenerDeDocente(bitacoraId, docente);
        List<Registro> enviados = enviados(bitacoraId);

        List<String> fechas = enviados.stream().map(Registro::getFechaClase).distinct().toList();
        List<String> resumen = enviados.stream()
                .map(r -> r.getContenido() + " - " + r.getActividad())
                .distinct()
                .toList();

        return new InformeMensualResponse(
                b.getCurso(), b.getAsignatura(), b.getMes(), b.getAnio(), b.getDocente(),
                b.getTitulo(), b.getProposito(), fechas, resumen,
                calcular(b, enviados), b.getFechaCierre(), Utilidades.fechaHoy());
    }

    private List<Registro> enviados(Long bitacoraId) {
        return registroRepository.findByBitacoraIdOrderByIdAsc(bitacoraId).stream()
                .filter(r -> !RegistroService.BORRADOR.equals(r.getEstado()))
                .toList();
    }

    private ParticipacionResponse calcular(Bitacora b, List<Registro> enviados) {
        List<String> estudiantesCurso = estudianteRepository.findByCurso(b.getCurso()).stream()
                .map(Estudiante::getUsuario)
                .toList();
        Set<String> participaron = enviados.stream().map(Registro::getEstudiante).collect(Collectors.toSet());
        List<String> sinParticipacion = estudiantesCurso.stream()
                .filter(u -> !participaron.contains(u))
                .toList();

        int revisados = (int) enviados.stream().filter(r -> RegistroService.REVISADO.equals(r.getEstado())).count();
        int pendientes = (int) enviados.stream().filter(r -> RegistroService.PENDIENTE.equals(r.getEstado())).count();

        return new ParticipacionResponse(
                b.getId(), b.getCurso(), b.getAsignatura(),
                estudiantesCurso.size(),
                estudiantesCurso.size() - sinParticipacion.size(),
                sinParticipacion.size(),
                sinParticipacion,
                enviados.size(), revisados, pendientes);
    }
}
