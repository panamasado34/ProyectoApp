package cl.duoc.educacional.controller;

import cl.duoc.educacional.model.Asignacion;
import cl.duoc.educacional.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    // 1. GET: Listar todas
    @GetMapping
    public ResponseEntity<List<Asignacion>> listar() {
        return ResponseEntity.ok(asignacionService.findAll());
    }

    // 2. GET: Cursos y asignaturas de un docente
    @GetMapping("/docente/{usuario}")
    public ResponseEntity<List<Asignacion>> porDocente(@PathVariable String usuario) {
        return ResponseEntity.ok(asignacionService.listarPorDocente(usuario));
    }

    // 3. GET: Asignaturas de un estudiante (segun su curso)
    @GetMapping("/estudiante/{usuario}")
    public ResponseEntity<List<Asignacion>> porEstudiante(@PathVariable String usuario) {
        return ResponseEntity.ok(asignacionService.listarPorEstudiante(usuario));
    }

    // 4. POST: Registrar / Crear
    @PostMapping
    public ResponseEntity<Asignacion> registrar(@RequestBody Asignacion asignacion) {
        return new ResponseEntity<>(asignacionService.registrar(asignacion), HttpStatus.CREATED);
    }

    // 5. DELETE: Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (asignacionService.borrar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
