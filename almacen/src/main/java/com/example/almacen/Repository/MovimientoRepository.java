package com.example.almacen.Repository;

import com.example.almacen.modelo.Movimiento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    @EntityGraph(attributePaths = {"usuario", "proveedor"})
    List<Movimiento> findAllByOrderByFechaDesc();
}
