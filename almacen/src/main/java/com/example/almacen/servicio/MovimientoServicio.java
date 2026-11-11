package com.example.almacen.servicio;

import com.example.almacen.Repository.MovimientoRepository;
import com.example.almacen.Repository.ProductoRepository;
import com.example.almacen.Repository.ProveedorRepository;
import com.example.almacen.Repository.UsuarioRepository;
import com.example.almacen.formulario.DetalleMovimientoForm;
import com.example.almacen.modelo.DetalleMovimiento;
import com.example.almacen.modelo.Movimiento;
import com.example.almacen.modelo.Producto;
import com.example.almacen.modelo.Proveedor;
import com.example.almacen.modelo.TipoMovimiento;
import com.example.almacen.modelo.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoServicio {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;

    public MovimientoServicio(MovimientoRepository movimientoRepository,
                              ProductoRepository productoRepository,
                              ProveedorRepository proveedorRepository,
                              UsuarioRepository usuarioRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void registrar(String correoUsuario, TipoMovimiento tipo, Integer proveedorId,
                          String observacion, List<DetalleMovimientoForm> detalles) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correoUsuario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario de la sesión."));

        Proveedor proveedor = null;
        if (tipo == TipoMovimiento.ENTRADA) {
            if (proveedorId == null) {
                throw new IllegalArgumentException("Selecciona el proveedor de la entrada.");
            }
            proveedor = proveedorRepository.findById(proveedorId)
                    .orElseThrow(() -> new IllegalArgumentException("El proveedor seleccionado no existe."));
        }

        Movimiento movimiento = new Movimiento(usuario, proveedor, tipo, limpiar(observacion));
        for (DetalleMovimientoForm formulario : detalles) {
            Producto producto = productoRepository.findById(formulario.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Uno de los productos ya no existe."));
            if (!producto.isEstado()) {
                throw new IllegalArgumentException("No se puede mover un producto inactivo.");
            }

            BigDecimal costo = formulario.getCostoUnitario();
            movimiento.agregarDetalle(new DetalleMovimiento(producto,
                    formulario.getCantidad(), costo));
        }

        // El disparador de MariaDB actualiza el stock al insertar cada detalle.
        // saveAndFlush fuerza ese paso dentro de esta transacción.
        movimientoRepository.saveAndFlush(movimiento);
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
