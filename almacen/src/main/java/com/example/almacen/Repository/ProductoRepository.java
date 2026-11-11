package com.example.almacen.Repository;

import com.example.almacen.modelo.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @EntityGraph(attributePaths = "categoria")
    List<Producto> findAllByOrderByNombreAsc();

    @EntityGraph(attributePaths = "categoria")
    List<Producto> findByEstadoTrueOrderByNombreAsc();

    Optional<Producto> findByCodigoIgnoreCase(String codigo);
}
