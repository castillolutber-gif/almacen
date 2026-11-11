package com.example.almacen.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "fk_mov_usuario"))
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor",
            foreignKey = @ForeignKey(name = "fk_mov_proveedor"))
    private Proveedor proveedor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column(insertable = false, updatable = false)
    private LocalDateTime fecha;

    @Column(length = 255)
    private String observacion;

    @OneToMany(mappedBy = "movimiento", cascade = CascadeType.PERSIST)
    private List<DetalleMovimiento> detalles = new ArrayList<>();

    protected Movimiento() {
    }

    public Movimiento(Usuario usuario, Proveedor proveedor, TipoMovimiento tipo, String observacion) {
        this.usuario = usuario;
        this.proveedor = proveedor;
        this.tipo = tipo;
        this.observacion = observacion;
    }

    public Integer getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public TipoMovimiento getTipo() { return tipo; }
    public void setTipo(TipoMovimiento tipo) { this.tipo = tipo; }
    public LocalDateTime getFecha() { return fecha; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public List<DetalleMovimiento> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleMovimiento detalle) {
        detalles.add(detalle);
        detalle.setMovimiento(this);
    }
}
