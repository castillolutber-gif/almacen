package com.example.almacen.controlador;

import com.example.almacen.Repository.UsuarioRepository;
import com.example.almacen.modelo.RolUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("configuracionDisponible", !usuarioRepository
                .existsByRolAndEstadoTrueAndPasswordHashStartingWith(RolUsuario.ADMIN, "$2"));
        return "login";
    }
}
