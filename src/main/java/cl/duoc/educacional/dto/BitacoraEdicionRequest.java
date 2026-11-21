package cl.duoc.educacional.dto;

public record BitacoraEdicionRequest(
        String titulo,
        String proposito,
        String instrucciones,
        String preguntas
) {
}
