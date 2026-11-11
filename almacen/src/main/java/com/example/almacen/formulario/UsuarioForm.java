package com.example.almacen.formulario;

import com.example.almacen.modelo.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioForm {

    private Integer id;

    @NotBlank(message = "Escribe el nombre.")
    @Size(max = 100, message = "El nombre admite hasta 100 caracteres.")
    private String nombre;

    @NotBlank(message = "Escribe el correo.")
    @Email(message = "Escribe un correo válido.")
    @Size(max = 120, message = "El correo admite hasta 120 caracteres.")
    private String correo;

    private String password;

    @NotNull(message = "Selecciona el rol.")
    private RolUsuario rol;

    private boolean estado = true;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
