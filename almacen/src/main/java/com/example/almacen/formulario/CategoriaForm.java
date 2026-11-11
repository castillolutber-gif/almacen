package com.example.almacen.formulario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaForm {

    private Integer id;

    @NotBlank(message = "Escribe el nombre de la categoría.")
    @Size(max = 80, message = "El nombre admite hasta 80 caracteres.")
    private String nombre;

    @Size(max = 200, message = "La descripción admite hasta 200 caracteres.")
    private String descripcion;

    private boolean estado = true;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
