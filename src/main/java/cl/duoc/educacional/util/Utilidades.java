package cl.duoc.educacional.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Utilidades {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Utilidades() {
    }

    /** Fecha de hoy con el mismo formato que usa la app Android (dd/MM/yyyy). */
    public static String fechaHoy() {
        return LocalDate.now().format(FORMATO);
    }

    /** La app Android trabaja con "" en vez de null para los textos opcionales. */
    public static String vacioSiNulo(String valor) {
        return valor == null ? "" : valor;
    }

    /** Valida que un texto obligatorio no venga nulo ni vacio (responde 400). */
    public static String requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El campo '" + campo + "' es obligatorio");
        }
        return valor;
    }
}
