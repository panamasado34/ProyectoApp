package cl.duoc.educacional.service;

import cl.duoc.educacional.dto.LoginRequest;
import cl.duoc.educacional.dto.LoginResponse;
import cl.duoc.educacional.model.Docente;
import cl.duoc.educacional.model.Estudiante;
import cl.duoc.educacional.repository.DocenteRepository;
import cl.duoc.educacional.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    /** Devuelve los datos del usuario si las credenciales son correctas, o null si no. */
    public LoginResponse login(LoginRequest request) {
        if (request == null || request.usuario() == null || request.password() == null
                || request.perfil() == null) {
            return null;
        }
        if (request.perfil().equals("docente")) {
            Docente d = docenteRepository.findByUsuario(request.usuario()).orElse(null);
            if (d != null && d.getPassword().equals(request.password())) {
                return new LoginResponse(d.getUsuario(), d.getNombre(), "docente", "");
            }
        } else if (request.perfil().equals("estudiante")) {
            Estudiante e = estudianteRepository.findByUsuario(request.usuario()).orElse(null);
            if (e != null && e.getPassword().equals(request.password())) {
                return new LoginResponse(e.getUsuario(), e.getNombre(), "estudiante", e.getCurso());
            }
        }
        return null;
    }
}
