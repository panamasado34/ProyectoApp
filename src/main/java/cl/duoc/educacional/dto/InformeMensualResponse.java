package cl.duoc.educacional.dto;

import java.util.List;

/** Datos del informe mensual. La app Android los usa para armar el PDF. */
public record InformeMensualResponse(
        String curso,
        String asignatura,
        String mes,
        String anio,
        String docente,
        String titulo,
        String proposito,
        List<String> fechasRegistros,
        List<String> resumenTemasActividades,
        ParticipacionResponse participacion,
        String fechaCierre,
        String fechaGeneracion
) {
}
