package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Recordatorio;
import cl.duoc.educacional.repository.DocenteRepository;
import cl.duoc.educacional.repository.RecordatorioRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecordatorioService {
    public static final String PENDIENTE = "pendiente";
    public static final String COMPLETADO = "completado";

    @Autowired
    private RecordatorioRepository recordatorioRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    // 1. Recordatorios de un docente
    public List<Recordatorio> listarPorDocente(String docente) {
        return recordatorioRepository.findByDocente(docente);
    }

    // 2. Crear (queda pendiente)
    public Recordatorio crear(Recordatorio recordatorio) {
        Utilidades.requerido(recordatorio.getDocente(), "docente");
        Utilidades.requerido(recordatorio.getTipo(), "tipo");
        Utilidades.requerido(recordatorio.getFechaProgramada(), "fechaProgramada");
        if (!docenteRepository.existsByUsuario(recordatorio.getDocente())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no existe");
        }
        recordatorio.setId(null);
        recordatorio.setEstado(PENDIENTE);
        return recordatorioRepository.save(recordatorio);
    }

    // 3. Completar
    public Recordatorio completar(Long id, String docente) {
        Recordatorio r = recordatorioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recordatorio no existe"));
        if (docente == null || !r.getDocente().equals(docente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este recordatorio no es tuyo");
        }
        r.setEstado(COMPLETADO);
        return recordatorioRepository.save(r);
    }
}
