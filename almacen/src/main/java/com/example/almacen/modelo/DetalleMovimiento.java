package com.example.almacen.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_movimiento")
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_movimiento", nullable = false,
            foreignKey = @ForeignKey(name = "fk_det_movimiento"))
    private Movimiento movimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_det_producto"))
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "costo_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    protected DetalleMovimiento() {
    }

    public DetalleMovimiento(Producto producto, Integer cantidad, BigDecimal costoUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
    }

    public Integer getId() { return id; }
    public Movimiento getMovimiento() { return movimiento; }
    public void setMovimiento(Movimiento movimiento) { this.movimiento = movimiento; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
}
