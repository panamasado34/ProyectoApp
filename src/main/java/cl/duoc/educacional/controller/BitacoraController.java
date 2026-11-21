package cl.duoc.educacional.controller;

import cl.duoc.educacional.dto.BitacoraEdicionRequest;
import cl.duoc.educacional.dto.InformeMensualResponse;
import cl.duoc.educacional.dto.ParticipacionResponse;
import cl.duoc.educacional.model.Bitacora;
import cl.duoc.educacional.service.BitacoraService;
import cl.duoc.educacional.service.InformeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bitacoras")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private InformeService informeService;

    // 1. GET: Bitacoras del docente (historial): /bitacoras/docente/docente1
    @GetMapping("/docente/{usuario}")
    public ResponseEntity<List<Bitacora>> listarDeDocente(@PathVariable String usuario) {
        return ResponseEntity.ok(bitacoraService.listarPorDocente(usuario));
    }

    // 2. GET: Bitacoras publicadas/cerradas del curso del estudiante
    @GetMapping("/estudiante/{usuario}")
    public ResponseEntity<List<Bitacora>> listarParaEstudiante(@PathVariable String usuario) {
        return ResponseEntity.ok(bitacoraService.listarParaEstudiante(usuario));
    }

    // 3. GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Bitacora> buscarPorId(@PathVariable Long id) {
        Bitacora bitacora = bitacoraService.buscarPorId(id);
        if (bitacora == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bitacora);
    }

    // 4. POST: Crear bitacora mensual (nace en borrador)
    @PostMapping
    public ResponseEntity<Bitacora> crear(@RequestBody Bitacora bitacora) {
        return new ResponseEntity<>(bitacoraService.crear(bitacora), HttpStatus.CREATED);
    }

    // 5. PUT: Publicar  (/bitacoras/1/publicar?docente=docente1)
    @PutMapping("/{id}/publicar")
    public ResponseEntity<Bitacora> publicar(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(bitacoraService.publicar(id, docente));
    }

    // 6. PUT: Cerrar    (/bitacoras/1/cerrar?docente=docente1)
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Bitacora> cerrar(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(bitacoraService.cerrar(id, docente));
    }

    // PUT: Editar bitacora en borrador (/bitacoras/1?docente=docente1)
    @PutMapping("/{id}")
    public ResponseEntity<Bitacora> editar(
            @PathVariable Long id,
            @RequestParam String docente,
            @RequestBody BitacoraEdicionRequest req) {
        return ResponseEntity.ok(bitacoraService.editar(id, docente, req));
    }

    // 7. GET: Indicadores de participacion (solo docente responsable)
    @GetMapping("/{id}/participacion")
    public ResponseEntity<ParticipacionResponse> participacion(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(informeService.participacion(id, docente));
    }

    // 8. GET: Datos del informe mensual (solo docente responsable)
    @GetMapping("/{id}/informe")
    public ResponseEntity<InformeMensualResponse> informe(@PathVariable Long id, @RequestParam String docente) {
        return ResponseEntity.ok(informeService.informe(id, docente));
    }
}
