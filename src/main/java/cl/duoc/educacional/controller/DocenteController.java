package cl.duoc.educacional.controller;

import cl.duoc.educacional.model.Docente;
import cl.duoc.educacional.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    // 1. GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Docente>> listar() {
        return ResponseEntity.ok(docenteService.findAll());
    }

    // 2. GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Docente> buscarPorId(@PathVariable Long id) {
        Docente docente = docenteService.buscarPorId(id);
        if (docente == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(docente);
    }

    // 3. POST: Registrar / Crear
    @PostMapping
    public ResponseEntity<Docente> registrar(@RequestBody Docente docente) {
        return new ResponseEntity<>(docenteService.registrar(docente), HttpStatus.CREATED);
    }

    // 4. PUT: Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizar(@PathVariable Long id, @RequestBody Docente docente) {
        Docente actualizado = docenteService.actualizar(id, docente);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. DELETE: Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        if (docenteService.borrar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
