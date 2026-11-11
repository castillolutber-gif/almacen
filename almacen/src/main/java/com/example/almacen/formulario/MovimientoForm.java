package com.example.almacen.formulario;

import com.example.almacen.modelo.TipoMovimiento;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class MovimientoForm {

    private TipoMovimiento tipo;
    private Integer proveedorId;

    @Size(max = 255, message = "La observación admite hasta 255 caracteres.")
    private String observacion;

    private List<DetalleMovimientoForm> detalles = new ArrayList<>(List.of(new DetalleMovimientoForm()));

    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }
    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public List<DetalleMovimientoForm> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleMovimientoForm> detalles) { this.detalles = detalles; }
}
