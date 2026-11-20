package cl.duoc.educacional.controller;

import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    // 1. GET: Listar todos (o filtrar por curso: /estudiantes?curso=7.º Básico A)
    @GetMapping
    public ResponseEntity<List<Estudiante>> listar(@RequestParam(required = false) String curso) {
        if (curso != null && !curso.isBlank()) {
            return ResponseEntity.ok(estudianteService.buscarPorCurso(curso));
        }
        return ResponseEntity.ok(estudianteService.findAll());
    }

    // 2. GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> buscarPorId(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);
        if (estudiante == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estudiante);
    }

    // 3. POST: Registrar / Crear
    @PostMapping
    public ResponseEntity<Estudiante> registrar(@RequestBody Estudiante estudiante) {
        return new ResponseEntity<>(estudianteService.registrar(estudiante), HttpStatus.CREATED);
    }

    // 4. PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Estudiante actualizado = estudianteService.actualizar(id, estudiante);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. DELETE: Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (estudianteService.borrar(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}
