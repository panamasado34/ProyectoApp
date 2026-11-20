package cl.duoc.educacional.controller;

import cl.duoc.educacional.dto.ComentarioRequest;
import cl.duoc.educacional.model.Registro;
import cl.duoc.educacional.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    // ---------------------- ESTUDIANTE ----------------------

    // 1. POST: Crear registro (queda como borrador)
    @PostMapping
    public ResponseEntity<Registro> crear(@RequestBody Registro registro) {
        return new ResponseEntity<>(registroService.crear(registro), HttpStatus.CREATED);
    }

    // 2. PUT: Editar borrador  (/registros/5?estudiante=estudiante1)
    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizar(@PathVariable Long id, @RequestParam String estudiante,
                                               @RequestBody Registro registro) {
        return ResponseEntity.ok(registroService.actualizar(id, registro, estudiante));
    }

    // 3. PUT: Enviar al docente (borrador -> pendiente)
    @PutMapping("/{id}/enviar")
    public ResponseEntity<Registro> enviar(@PathVariable Long id, @RequestParam String estudiante) {
        return ResponseEntity.ok(registroService.enviar(id, estudiante));
    }

    // 4. DELETE: Eliminar borrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, @RequestParam String estudiante) {
        registroService.eliminar(id, estudiante);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 5. GET: Historial personal (/registros/estudiante/estudiante1?bitacoraId=1)
    @GetMapping("/estudiante/{usuario}")
    public ResponseEntity<List<Registro>> historial(@PathVariable String usuario,
                                                    @RequestParam(required = false) Long bitacoraId) {
        return ResponseEntity.ok(registroService.listarPorEstudiante(usuario, bitacoraId));
    }

    // ---------------------- DOCENTE ----------------------

    // 6. GET: Aportes de una bitacora con filtros opcionales
    //    /registros/bitacora/1?docente=docente1&estudiante=estudiante2&fecha=10/11/2026&estado=pendiente
    @GetMapping("/bitacora/{bitacoraId}")
    public ResponseEntity<List<Registro>> listarParaDocente(@PathVariable Long bitacoraId,
                                                            @RequestParam String docente,
                                                            @RequestParam(required = false) String estudiante,
                                                            @RequestParam(required = false) String fecha,
                                                            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(registroService.listarParaDocente(bitacoraId, docente, estudiante, fecha, estado));
    }

    // 7. GET: Aportes pendientes de revision
    @GetMapping("/bitacora/{bitacoraId}/pendientes")
    public ResponseEntity<List<Registro>> pendientes(@PathVariable Long bitacoraId, @RequestParam String docente) {
        return ResponseEntity.ok(registroService.listarPendientes(bitacoraId, docente));
    }

    // 8. PUT: Retroalimentacion  (/registros/5/comentario?docente=docente1)
    @PutMapping("/{id}/comentario")
    public ResponseEntity<Registro> comentar(@PathVariable Long id, @RequestParam String docente,
                                             @RequestBody ComentarioRequest request) {
        return ResponseEntity.ok(registroService.comentar(id, docente, request.comentario()));
    }

    // 9. PUT: Marcar como revisado
    @PutMapping("/{id}/revisar")
    public ResponseEntity<Registro> revisar(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(registroService.marcarRevisado(id, docente));
    }
}
