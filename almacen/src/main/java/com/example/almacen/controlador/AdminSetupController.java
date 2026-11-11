package com.example.almacen.controlador;

import com.example.almacen.Repository.UsuarioRepository;
import com.example.almacen.formulario.AdminSetupForm;
import com.example.almacen.modelo.RolUsuario;
import com.example.almacen.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.InetAddress;

@Controller
public class AdminSetupController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSetupController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/configurar-admin")
    public String mostrar(HttpServletRequest request, Model model) {
        if (!esAccesoLocal(request)) {
            return "error/configuracion-cerrada";
        }
        if (yaExisteAdminConfigurado()) {
            return "redirect:/login";
        }
        model.addAttribute("form", new AdminSetupForm());
        return "configurar-admin";
    }

    @PostMapping("/configurar-admin")
    public String guardar(HttpServletRequest request,
                          @Valid @ModelAttribute("form") AdminSetupForm form,
                          BindingResult errores, Model model) {
        if (!esAccesoLocal(request)) {
            return "error/configuracion-cerrada";
        }
        if (yaExisteAdminConfigurado()) {
            return "redirect:/login";
        }

        if (form.getPassword() != null && form.getPassword().length() > 72) {
            errores.rejectValue("password", "largo", "La contraseña admite hasta 72 caracteres.");
        }
        if (form.getPassword() != null && !form.getPassword().equals(form.getConfirmarPassword())) {
            errores.rejectValue("confirmarPassword", "noCoincide", "Las contraseñas no coinciden.");
        }
        if (form.getCorreo() != null && usuarioRepository.existsByCorreoIgnoreCase(form.getCorreo().trim())) {
            errores.rejectValue("correo", "duplicado", "Ese correo ya está registrado. Usa otro para el nuevo administrador.");
        }

        if (errores.hasErrors()) {
            return "configurar-admin";
        }

        Usuario administrador = new Usuario(form.getNombre().trim(), form.getCorreo().trim(),
                passwordEncoder.encode(form.getPassword()), RolUsuario.ADMIN);
        usuarioRepository.save(administrador);
        return "redirect:/login?configured";
    }

    private boolean yaExisteAdminConfigurado() {
        return usuarioRepository.existsByRolAndEstadoTrueAndPasswordHashStartingWith(RolUsuario.ADMIN, "$2");
    }

    private boolean esAccesoLocal(HttpServletRequest request) {
        try {
            return InetAddress.getByName(request.getRemoteAddr()).isLoopbackAddress();
        } catch (Exception ignorado) {
            return false;
        }
    }
}
