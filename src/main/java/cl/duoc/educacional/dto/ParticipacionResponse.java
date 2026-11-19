package cl.duoc.educacional.dto;

import java.util.List;

/** Indicadores de participacion de una bitacora (solo visible para el docente responsable). */
public record ParticipacionResponse(
        Long bitacoraId,
        String curso,
        String asignatura,
        int totalEstudiantes,
        int estudiantesParticiparon,
        int estudiantesSinParticipacion,
        List<String> sinParticipacion,
        int totalAportes,
        int aportesRevisados,
        int aportesPendientes
) {
}
