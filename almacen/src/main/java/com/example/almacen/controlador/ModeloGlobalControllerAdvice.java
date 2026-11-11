package com.example.almacen.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ModeloGlobalControllerAdvice {

    @ModelAttribute("esAdmin")
    public boolean esAdmin(Authentication autenticacion) {
        if (autenticacion == null) {
            return false;
        }
        return autenticacion.getAuthorities().stream()
                .anyMatch(autoridad -> autoridad.getAuthority().equals("ROLE_ADMIN"));
    }

    @ModelAttribute("usuarioActual")
    public String usuarioActual(Authentication autenticacion) {
        return autenticacion == null ? "" : autenticacion.getName();
    }
}
