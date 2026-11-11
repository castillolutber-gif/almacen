package com.example.almacen.formulario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminSetupForm {

    @NotBlank(message = "Escribe tu nombre.")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres.")
    private String nombre;

    @NotBlank(message = "Escribe tu correo.")
    @Email(message = "Escribe un correo válido.")
    @Size(max = 120, message = "El correo admite hasta 120 caracteres.")
    private String correo;

    @NotBlank(message = "Crea una contraseña.")
    @Size(min = 12, max = 72, message = "La contraseña debe tener entre 12 y 72 caracteres.")
    private String password;

    @NotBlank(message = "Confirma la contraseña.")
    private String confirmarPassword;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmarPassword() { return confirmarPassword; }
    public void setConfirmarPassword(String confirmarPassword) { this.confirmarPassword = confirmarPassword; }
}
