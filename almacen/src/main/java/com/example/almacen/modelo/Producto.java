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
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @Column(nullable = false, length = 30, unique = true)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    // El stock lo mantienen los disparadores al registrar detalles de movimiento.
    @Column(nullable = false, insertable = false, updatable = false)
    private Integer stock;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 5;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false,
            foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;

    @Column(nullable = false)
    private boolean estado = true;

    protected Producto() {
    }

    public Producto(String codigo, String nombre, String descripcion, BigDecimal precioCompra,
                    BigDecimal precioVenta, Integer stockMinimo, Categoria categoria, boolean estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockMinimo = stockMinimo;
        this.categoria = categoria;
        this.estado = estado;
    }

    public Integer getId() { return id; }
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
    public Integer getStock() { return stock; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
