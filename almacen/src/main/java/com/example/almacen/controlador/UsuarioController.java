package com.example.almacen.controlador;

import com.example.almacen.Repository.UsuarioRepository;
import com.example.almacen.formulario.UsuarioForm;
import com.example.almacen.modelo.RolUsuario;
import com.example.almacen.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAllByOrderByNombreAsc());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new UsuarioForm());
        cargarRoles(model);
        return "usuarios/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        UsuarioForm form = new UsuarioForm();
        form.setId(usuario.getId());
        form.setNombre(usuario.getNombre());
        form.setCorreo(usuario.getCorreo());
        form.setRol(usuario.getRol());
        form.setEstado(usuario.isEstado());
        model.addAttribute("form", form);
        cargarRoles(model);
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("form") UsuarioForm form,
                          BindingResult errores, Model model, RedirectAttributes mensajes) {
        usuarioRepository.findByCorreoIgnoreCase(form.getCorreo())
                .filter(existente -> !existente.getId().equals(form.getId()))
                .ifPresent(existente -> errores.rejectValue("correo", "duplicado",
                        "Ya existe una cuenta con ese correo."));

        String password = form.getPassword() == null ? "" : form.getPassword();
        if (form.getId() == null && password.length() < 12) {
            errores.rejectValue("password", "corto", "La contraseña inicial debe tener al menos 12 caracteres.");
        } else if (!password.isBlank() && password.length() < 12) {
            errores.rejectValue("password", "corto", "La nueva contraseña debe tener al menos 12 caracteres.");
        }

        if (errores.hasErrors()) {
            cargarRoles(model);
            return "usuarios/formulario";
        }

        Usuario usuario;
        if (form.getId() == null) {
            usuario = new Usuario(form.getNombre().trim(), form.getCorreo().trim(),
                    passwordEncoder.encode(password), form.getRol());
        } else {
            usuario = usuarioRepository.findById(form.getId()).orElseThrow();
            usuario.setNombre(form.getNombre().trim());
            usuario.setCorreo(form.getCorreo().trim());
            usuario.setRol(form.getRol());
            usuario.setEstado(form.isEstado());
            if (!password.isBlank()) {
                usuario.setPasswordHash(passwordEncoder.encode(password));
            }
        }
        usuarioRepository.save(usuario);
        mensajes.addFlashAttribute("exito", "Usuario guardado. La contraseña se almacena cifrada con BCrypt.");
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes mensajes) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setEstado(!usuario.isEstado());
        usuarioRepository.save(usuario);
        mensajes.addFlashAttribute("exito", "Estado de la cuenta actualizado.");
        return "redirect:/usuarios";
    }

    private void cargarRoles(Model model) {
        model.addAttribute("roles", RolUsuario.values());
    }
}
