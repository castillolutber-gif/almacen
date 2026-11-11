package com.example.almacen.formulario;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductoForm {

    private Integer id;

    @NotBlank(message = "Escribe el código del producto.")
    @Size(max = 30, message = "El código admite hasta 30 caracteres.")
    private String codigo;

    @NotBlank(message = "Escribe el nombre del producto.")
    @Size(max = 120, message = "El nombre admite hasta 120 caracteres.")
    private String nombre;

    @Size(max = 255, message = "La descripción admite hasta 255 caracteres.")
    private String descripcion;

    @NotNull(message = "Indica el precio de compra.")
    @DecimalMin(value = "0.00", message = "El precio de compra no puede ser negativo.")
    private BigDecimal precioCompra;

    @NotNull(message = "Indica el precio de venta.")
    @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor que cero.")
    private BigDecimal precioVenta;

    @NotNull(message = "Indica el stock mínimo.")
    @PositiveOrZero(message = "El stock mínimo no puede ser negativo.")
    private Integer stockMinimo = 5;

    @NotNull(message = "Selecciona una categoría.")
    private Integer categoriaId;

    private boolean estado = true;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
