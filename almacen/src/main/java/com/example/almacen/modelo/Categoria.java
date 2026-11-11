package com.example.almacen.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @Column(nullable = false, length = 80, unique = true)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private boolean estado = true;

    protected Categoria() {
    }

    public Categoria(String nombre, String descripcion, boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
