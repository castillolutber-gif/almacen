package com.example.almacen.formulario;

import java.math.BigDecimal;

public class DetalleMovimientoForm {

    private Integer productoId;
    private Integer cantidad;
    private BigDecimal costoUnitario;

    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }

    public boolean estaVacio() {
        return productoId == null && cantidad == null && costoUnitario == null;
    }
}
