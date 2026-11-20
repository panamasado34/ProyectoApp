package cl.duoc.educacional.controller;

import cl.duoc.educacional.dto.LoginRequest;
import cl.duoc.educacional.dto.LoginResponse;
import cl.duoc.educacional.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST: Iniciar sesion con cuenta ficticia
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse respuesta = authService.login(request);
        if (respuesta == null) {
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }
        return ResponseEntity.ok(respuesta);
    }
}
