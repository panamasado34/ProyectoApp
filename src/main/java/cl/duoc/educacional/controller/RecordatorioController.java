package cl.duoc.educacional.controller;

import cl.duoc.educacional.model.Recordatorio;
import cl.duoc.educacional.service.RecordatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recordatorios")
public class RecordatorioController {

    @Autowired
    private RecordatorioService recordatorioService;

    // 1. GET: Recordatorios de un docente  (/recordatorios?docente=docente1)
    @GetMapping
    public ResponseEntity<List<Recordatorio>> listar(@RequestParam String docente) {
        return ResponseEntity.ok(recordatorioService.listarPorDocente(docente));
    }

    // 2. POST: Crear (queda pendiente)
    @PostMapping
    public ResponseEntity<Recordatorio> crear(@RequestBody Recordatorio recordatorio) {
        return new ResponseEntity<>(recordatorioService.crear(recordatorio), HttpStatus.CREATED);
    }

    // 3. PUT: Completar  (/recordatorios/1/completar?docente=docente1)
    @PutMapping("/{id}/completar")
    public ResponseEntity<Recordatorio> completar(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(recordatorioService.completar(id, docente));
    }
}
