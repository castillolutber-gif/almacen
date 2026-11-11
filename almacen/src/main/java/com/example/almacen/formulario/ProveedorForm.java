package com.example.almacen.formulario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProveedorForm {

    private Integer id;

    @NotBlank(message = "Escribe la razón social.")
    @Size(max = 120, message = "La razón social admite hasta 120 caracteres.")
    private String razonSocial;

    @Pattern(regexp = "(^$|[0-9]{11})", message = "El RUC debe tener 11 dígitos.")
    private String ruc;

    @Size(max = 20, message = "El teléfono admite hasta 20 caracteres.")
    private String telefono;

    @Email(message = "Escribe un correo válido.")
    @Size(max = 120, message = "El correo admite hasta 120 caracteres.")
    private String correo;

    @Size(max = 200, message = "La dirección admite hasta 200 caracteres.")
    private String direccion;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
