package cl.duoc.educacional.service;

import cl.duoc.educacional.model.Docente;
import cl.duoc.educacional.repository.DocenteRepository;
import cl.duoc.educacional.util.Utilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;

    // 1. Listar todos
    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    // 2. Buscar por ID
    public Docente buscarPorId(Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    // 3. Registrar (Crear)
    public Docente registrar(Docente docente) {
        Utilidades.requerido(docente.getUsuario(), "usuario");
        Utilidades.requerido(docente.getPassword(), "password");
        Utilidades.requerido(docente.getNombre(), "nombre");
        if (docenteRepository.existsByUsuario(docente.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        docente.setId(null);
        return docenteRepository.save(docente);
    }

    // 4. Borrar
    public boolean borrar(Long id) {
        if (docenteRepository.existsById(id)) {
            docenteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 5. Actualizar
    public Docente actualizar(Long id, Docente detalles) {
        return docenteRepository.findById(id).map(docente -> {
            docente.setNombre(Utilidades.requerido(detalles.getNombre(), "nombre"));
            if (detalles.getPassword() != null && !detalles.getPassword().isBlank()) {
                docente.setPassword(detalles.getPassword());
            }
            return docenteRepository.save(docente);
        }).orElse(null);
    }
}
