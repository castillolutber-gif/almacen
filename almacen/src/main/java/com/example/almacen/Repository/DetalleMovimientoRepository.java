package com.example.almacen.Repository;

import com.example.almacen.modelo.DetalleMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Integer> {
}
