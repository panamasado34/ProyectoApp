package cl.duoc.educacional.config;

import cl.duoc.educacional.model.Asignacion;
import cl.duoc.educacional.model.Bitacora;
import cl.duoc.educacional.model.Docente;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.model.Recordatorio;
import cl.duoc.educacional.repository.AsignacionRepository;
import cl.duoc.educacional.repository.BitacoraRepository;
import cl.duoc.educacional.repository.DocenteRepository;
import cl.duoc.educacional.repository.EstudianteRepository;
import cl.duoc.educacional.repository.RecordatorioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Carga datos FICTICIOS de prueba (los mismos de DatosPrueba.kt de la app Android).
 * No hay nombres, RUT ni correos reales.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatosDemo(EstudianteRepository estudianteRepository,
                                      DocenteRepository docenteRepository,
                                      AsignacionRepository asignacionRepository,
                                      BitacoraRepository bitacoraRepository,
                                      RecordatorioRepository recordatorioRepository) {
        return args -> {
            if (estudianteRepository.count() == 0) {
                estudianteRepository.saveAll(List.of(
                        new Estudiante(null, "estudiante1", "clave1234", "Angie", "7.º Básico A"),
                        new Estudiante(null, "estudiante2", "clave1234", "Leon S Kennedy", "7.º Básico A"),
                        new Estudiante(null, "estudiante3", "clave1234", "Estudiante Prueba 3", "7.º Básico A"),
                        new Estudiante(null, "estudiante4", "clave1234", "Estudiante Prueba 4", "8.º Básico B")
                ));
            }

            if (docenteRepository.count() == 0) {
                docenteRepository.saveAll(List.of(
                        new Docente(null, "docente1", "clave1234", "Docente Prueba 1"),
                        new Docente(null, "docente2", "clave1234", "Docente Prueba 2")
                ));
            }

            if (asignacionRepository.count() == 0) {
                asignacionRepository.saveAll(List.of(
                        new Asignacion(null, "docente1", "7.º Básico A", "Lenguaje"),
                        new Asignacion(null, "docente1", "7.º Básico A", "Matemática"),
                        new Asignacion(null, "docente2", "8.º Básico B", "Ciencias")
                ));
            }

            if (bitacoraRepository.count() == 0) {
                Bitacora b = new Bitacora();
                b.setDocente("docente1");
                b.setCurso("7.º Básico A");
                b.setAsignatura("Lenguaje");
                b.setMes("Noviembre");
                b.setAnio("2026");
                b.setTitulo("Bitácora de Lenguaje - Noviembre");
                b.setProposito("Registrar lo aprendido en cada clase de Lenguaje.");
                b.setInstrucciones("Después de cada clase completa tu registro y envíalo.");
                b.setPreguntas("¿Qué trabajamos hoy? ¿Qué aprendí? ¿Qué dudas me quedaron?");
                b.setEstado("publicada");
                b.setFechaPublicacion("02/11/2026");
                b.setFechaCierre("");
                bitacoraRepository.save(b);
            }

            if (recordatorioRepository.count() == 0) {
                recordatorioRepository.save(
                        new Recordatorio(null, "docente1", "Cierre mensual", "30/11/2026", "pendiente"));
            }
        };
    }
}
